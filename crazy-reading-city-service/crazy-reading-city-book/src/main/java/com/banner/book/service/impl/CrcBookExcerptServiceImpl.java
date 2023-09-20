package com.banner.book.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.banner.book.service.CrcBookScanService;
import com.banner.common.utils.ThreadLocalUtil;
import com.banner.model.book.dtos.CrcBookExcerptDto;
import com.banner.model.book.dtos.GetExcerptInfoDto;
import com.banner.model.book.pojos.CrcBookExcerpt;
import com.banner.book.mapper.CrcBookExcerptMapper;
import com.banner.book.service.CrcBookExcerptService;
import com.banner.model.common.dtos.PageResponseResult;
import com.banner.model.common.dtos.ResponseResult;
import com.banner.model.common.enums.AppHttpCodeEnum;
import com.banner.model.user.pojos.CrcUserComment;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xxl.job.core.handler.annotation.XxlJob;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.TimeUnit;

import static com.banner.book.utils.BookExcerptConstants.EXCERPT_PUBLIC_NOT;
import static com.banner.common.constants.LikeOrNotConstant.IS_LIKE;
import static com.banner.common.constants.LikeOrNotConstant.IS_NOT_LIKE;
import static com.banner.common.constants.RedisConstants.*;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author rjj
 * @since 2023-07-28
 */
@Service
@Slf4j
public class CrcBookExcerptServiceImpl extends ServiceImpl<CrcBookExcerptMapper, CrcBookExcerpt> implements CrcBookExcerptService {


    @Resource
    private CrcBookScanService crcBookScanService;

    @Override
    @Transactional
    public ResponseResult addExcerpt(CrcBookExcerptDto crcBookExcerptDto) {

        //保存信息
        CrcBookExcerpt crcBookExcerpt = BeanUtil.copyProperties(crcBookExcerptDto, CrcBookExcerpt.class);
        if (crcBookExcerpt.getUserId() == null) crcBookExcerpt.setUserId(ThreadLocalUtil.getId());
        crcBookExcerpt.setId(IdWorker.getId());

        boolean save = this.save(crcBookExcerpt);

        if (!save) {
            return PageResponseResult.errorResult(500, "书籍不存在");
        }

        if (crcBookExcerpt.getStatus().equals(EXCERPT_PUBLIC_NOT)
                && crcBookExcerpt.getPublishTime().isBefore(LocalDateTime.now())) {
            //公开未审核且发布时间在现在之前,去审核
            crcBookScanService.scanBook(crcBookExcerpt.getId());
        }
        //草稿或私密,不做处理
        return ResponseResult.okResult(200, "添加成功");
    }
    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @XxlJob("crcExcerptPublish")
    public void excerptPublish() {
        //读取数据库中未来5分钟以内的所有公开未发布的摘录
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MINUTE, 5);
        List<String> excerptIds = crcBookExcerptMapper.getExcerptTaskIds(calendar.getTime());
        for (String excerptId : excerptIds) {
            //加载到缓存中
            stringRedisTemplate.opsForList().leftPush(EXCERPT_PUBLISH_KEY,excerptId);
        }
    }

    @XxlJob("crcExcerptCheck")
    public void excerptCheck(){
        System.out.println("消费了");
        while (Boolean.TRUE.equals(stringRedisTemplate.hasKey(EXCERPT_PUBLISH_KEY))){
            String excerptId = stringRedisTemplate.opsForList().rightPop(EXCERPT_PUBLISH_KEY);
            crcBookScanService.scanBook(Long.valueOf(excerptId));
        }
    }


    @Override
    public ResponseResult getExcerpt(Long excerptId) {

        if (excerptId == null) return PageResponseResult.errorResult(AppHttpCodeEnum.PARAM_REQUIRE);

        //UV统计
        String keyUV = EXCERPT_UV_KEY + excerptId;
        stringRedisTemplate.opsForHyperLogLog().add(keyUV,
                ThreadLocalUtil.getId() + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd")));

        //查询缓存中是否存在
        String key = EXCERPT_CONTENT_KEY + excerptId;
        String json = stringRedisTemplate.opsForValue().get(key);
        GetExcerptInfoDto getExcerptInfoDto;
        if (StrUtil.isNotBlank(json)) {
            //命中,返回结果
            getExcerptInfoDto = JSONUtil.toBean(json, GetExcerptInfoDto.class);
            return ResponseResult.okResult(getExcerptInfoDto);
        }
        //未命中查询数据库
        CrcBookExcerpt crcBookExcerpt = this.getOne(Wrappers.<CrcBookExcerpt>lambdaQuery()
                .eq(CrcBookExcerpt::getId, excerptId));

        if (crcBookExcerpt != null) {
            getExcerptInfoDto = BeanUtil.copyProperties(crcBookExcerpt, GetExcerptInfoDto.class);
            //查询是否点赞
            if (ThreadLocalUtil.getId() != null
                    && stringRedisTemplate.opsForHash().get(LIKE_EXCERPT_KEY + excerptId, ThreadLocalUtil.getId().toString()) != null) {
                getExcerptInfoDto.setIsLike(IS_LIKE);
            } else {
                getExcerptInfoDto.setIsLike(IS_NOT_LIKE);
            }
            //查询uv统计
            getExcerptInfoDto.setUv(stringRedisTemplate.opsForHyperLogLog().size(keyUV));
            //添加到缓存
            stringRedisTemplate.opsForValue().set(key, JSONUtil.toJsonStr(getExcerptInfoDto), EXCERPT_CONTENT_TIME, TimeUnit.MINUTES);
            return PageResponseResult.okResult(getExcerptInfoDto);
        } else {
            //数据库与缓存中均不存在数据
            stringRedisTemplate.opsForHyperLogLog().delete(keyUV);
            return PageResponseResult.errorResult(AppHttpCodeEnum.DATA_NOT_EXIST);
        }

    }

    @Override
    public ResponseResult like(String excerptId, String userId) {
        if (StrUtil.isBlank(excerptId)) return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_REQUIRE);

        if (this.count(Wrappers.<CrcBookExcerpt>lambdaQuery()
                .select(CrcBookExcerpt::getId).eq(CrcBookExcerpt::getId, excerptId)) == 0)
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID);

        if (userId == null) userId = ThreadLocalUtil.getId().toString();

        String key = LIKE_EXCERPT_KEY + excerptId;
        //是否存在点赞
        Boolean isHave = stringRedisTemplate.hasKey(key);
        if (Boolean.TRUE.equals(isHave)) {
            //存在,查询该用户是否点赞
            Object status = stringRedisTemplate.opsForHash().get(key, userId);
            if (status != null && status.toString().equals(IS_LIKE)) {
                //点赞,现取消
                stringRedisTemplate.opsForHash().delete(key, userId);
                return ResponseResult.okResult(IS_NOT_LIKE);
            }
        }

        //未点赞,现点赞
        stringRedisTemplate.opsForHash().put(key, userId, IS_NOT_LIKE);
        return ResponseResult.okResult(IS_LIKE);

    }

    @Resource
    private CrcBookExcerptMapper crcBookExcerptMapper;

    @XxlJob("crcExcerptLike")
    public void flushedLike() {
        log.info("摘录点赞更新了");
        //获取所有点赞的评论
        Set<String> keys = stringRedisTemplate.keys(LIKE_EXCERPT_KEY + "*");
        if (keys != null && keys.size() != 0) {
            ArrayList<String> likeKeys = new ArrayList<>(keys);
            for (String likeKey : likeKeys) {
                //获取每个用户行为状态,将为0的更新到数据库
                Map<Object, Object> map = stringRedisTemplate.opsForHash().entries(likeKey);
                int count = 0;
                for (Object key : map.keySet().toArray()) {
                    //userId + status
                    String status = map.get(key).toString();
                    if (status.equals(IS_NOT_LIKE)) {
                        //修改redis中状态
                        stringRedisTemplate.opsForHash().put(likeKey, key.toString(), IS_LIKE);
                        count++;
                    }
                }
                if (count > 0) {
                    //存入数据库
                    String excerptId = likeKey.split(":")[2];
                    crcBookExcerptMapper.updateLikes(excerptId, count);
                }
            }
        }
    }

}
