package com.banner.user.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.banner.common.utils.ThreadLocalUtil;
import com.banner.model.common.dtos.PageResponseResult;
import com.banner.model.common.dtos.ResponseResult;
import com.banner.model.common.enums.AppHttpCodeEnum;
import com.banner.model.user.dtos.AddCRDto;
import com.banner.model.user.dtos.GetUserReplyDto;
import com.banner.model.user.dtos.GetUserReplyPlusDto;
import com.banner.model.user.pojos.CrcUserComment;
import com.banner.model.user.pojos.CrcUserReply;
import com.banner.user.mapper.CrcUserReplyMapper;
import com.banner.user.service.CrcUserReplyService;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xxl.job.core.handler.annotation.XxlJob;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static com.banner.common.constants.LikeOrNotConstant.IS_LIKE;
import static com.banner.common.constants.LikeOrNotConstant.IS_NOT_LIKE;
import static com.banner.common.constants.RedisConstants.LIKE_REPLY_KEY;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author rjj
 * @since 2023-08-01
 */
@Service
@Slf4j
public class CrcUserReplyServiceImpl extends ServiceImpl<CrcUserReplyMapper, CrcUserReply> implements CrcUserReplyService {

    @Resource
    private CrcUserReplyMapper crcUserReplyMapper;

    @Override
    public ResponseResult addReply(AddCRDto addCRDto) {
        CrcUserReply crcUserReply = BeanUtil.copyProperties(addCRDto, CrcUserReply.class);
        crcUserReply.setCommentId(addCRDto.getObjId());
        if (crcUserReply.getUserId() == null) crcUserReply.setUserId(ThreadLocalUtil.getId());


        boolean save = this.save(crcUserReply);

        return save ? PageResponseResult.okResult(200, "发布成功")
                : PageResponseResult.errorResult(500, "发布失败");
    }

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public List<GetUserReplyDto> listById(Long id) {

        List<GetUserReplyDto> getUserReplyDtos = crcUserReplyMapper.listById(id);

        for (GetUserReplyDto getUserReplyDto : getUserReplyDtos) {
            if (ThreadLocalUtil.getId() != null
                    && stringRedisTemplate.opsForHash().get(LIKE_REPLY_KEY + getUserReplyDto.getId().toString(), ThreadLocalUtil.getId().toString()) != null){
                //用户已点赞
                getUserReplyDto.setIsLike(IS_LIKE);
            }else {
                getUserReplyDto.setIsLike(IS_NOT_LIKE);
            }
        }

        return getUserReplyDtos;
    }

    @Override
    public List<GetUserReplyPlusDto> listPlusById(Long id) {

        List<GetUserReplyPlusDto> getUserReplyPlusDtos = crcUserReplyMapper.listPlusById(id);

        for (GetUserReplyPlusDto getUserReplyPlusDto : getUserReplyPlusDtos) {
            if (ThreadLocalUtil.getId() != null
                    && stringRedisTemplate.opsForHash().get(LIKE_REPLY_KEY + getUserReplyPlusDto.getId().toString(), ThreadLocalUtil.getId().toString()) != null){
                //用户已点赞
                getUserReplyPlusDto.setIsLike(IS_LIKE);
            }else {
                getUserReplyPlusDto.setIsLike(IS_NOT_LIKE);
            }
        }

        return getUserReplyPlusDtos;
    }

    @Override
    public ResponseResult like(String replyId, String userId) {
        if (StrUtil.isBlank(replyId)) return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_REQUIRE);

        if (this.count(Wrappers.<CrcUserReply>lambdaQuery()
                .select(CrcUserReply::getId).eq(CrcUserReply::getId,replyId))==0) return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID);

        if (userId==null) userId = ThreadLocalUtil.getId().toString();

        String key = LIKE_REPLY_KEY + replyId;
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

    @XxlJob("crcReplyLike")
    public void flushedLike() {
        //获取所有点赞的回复
        log.info("回复点赞更新了");
        Set<String> keys = stringRedisTemplate.keys(LIKE_REPLY_KEY + "*");
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
                    String replyId = likeKey.split(":")[2];
                    crcUserReplyMapper.updateLikes(replyId, count);
                }
            }
        }
    }

}
