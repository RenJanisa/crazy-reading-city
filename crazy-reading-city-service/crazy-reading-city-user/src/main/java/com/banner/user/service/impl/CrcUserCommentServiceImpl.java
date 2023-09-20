package com.banner.user.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.banner.common.utils.ThreadLocalUtil;
import com.banner.model.common.dtos.PageResponseResult;
import com.banner.model.common.dtos.ResponseResult;
import com.banner.model.common.enums.AppHttpCodeEnum;
import com.banner.model.user.dtos.*;
import com.banner.model.user.pojos.CrcUserComment;
import com.banner.model.user.pojos.CrcUserReply;
import com.banner.user.mapper.CrcUserCommentMapper;
import com.banner.user.mapper.CrcUserMapper;
import com.banner.user.mapper.CrcUserReplyMapper;
import com.banner.user.service.CrcUserCommentService;
import com.banner.user.service.CrcUserReplyService;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xxl.job.core.handler.annotation.XxlJob;
import lombok.extern.slf4j.Slf4j;
import org.checkerframework.checker.units.qual.A;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.lang.reflect.Array;
import java.util.*;
import java.util.stream.Collectors;

import static com.banner.common.constants.LikeOrNotConstant.IS_LIKE;
import static com.banner.common.constants.LikeOrNotConstant.IS_NOT_LIKE;
import static com.banner.common.constants.RedisConstants.LIKE_COMMENT_KEY;

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
public class CrcUserCommentServiceImpl extends ServiceImpl<CrcUserCommentMapper, CrcUserComment> implements CrcUserCommentService {

    @Override
    public ResponseResult addComment(AddCRDto addCRDto) {

        CrcUserComment crcUserComment = BeanUtil.copyProperties(addCRDto, CrcUserComment.class);
        if (crcUserComment.getUserId() == null) crcUserComment.setUserId(ThreadLocalUtil.getId());

        boolean save = this.save(crcUserComment);

        return save ? ResponseResult.okResult(200, "发布成功")
                : ResponseResult.errorResult(500, "发布失败");
    }

    @Resource
    private CrcUserReplyService crcUserReplyService;

    @Resource
    private CrcUserCommentMapper crcUserCommentMapper;
    @Resource
    private StringRedisTemplate stringRedisTemplate;


    @Override
    public ResponseResult getComment(Long objId) {
        if (objId == null) return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_REQUIRE);

        List<GetUserCommentDto> list = crcUserCommentMapper.listById(objId);
        if (list.isEmpty()) return ResponseResult.errorResult(500, "暂无评论");

        List<GetUserCommentDto> getUserCommentDtos = list.stream().peek(i -> {
            //查询评论的回复
            List<GetUserReplyDto> crcUserReplies = crcUserReplyService.listById(i.getId());
            //查询回复的回复
            List<GetUserReplyPlusDto> crcUserReplyPlus = crcUserReplyService.listPlusById(i.getId());
            i.setReplyPlus(crcUserReplyPlus);
            i.setReplies(crcUserReplies);
            if (ThreadLocalUtil.getId() != null
                    && stringRedisTemplate.opsForHash().get(LIKE_COMMENT_KEY + i.getId().toString(), ThreadLocalUtil.getId().toString()) != null){
                //用户已点赞
                i.setIsLike(IS_LIKE);
            }else {
                i.setIsLike(IS_NOT_LIKE);
            }
        }).collect(Collectors.toList());

        return ResponseResult.okResult(getUserCommentDtos);
    }


    @Override
    public ResponseResult like(String commentId, String userId) {

        if (StrUtil.isBlank(commentId)) return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_REQUIRE);

        if (this.count(Wrappers.<CrcUserComment>lambdaQuery()
                .select(CrcUserComment::getId).eq(CrcUserComment::getId,commentId))==0) return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID);

        if (userId==null) userId = ThreadLocalUtil.getId().toString();

        String key = LIKE_COMMENT_KEY + commentId;
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

    @XxlJob("crcCommentLike")
    public void flushedLike() {
        log.info("评论点赞更新了");
        //获取所有点赞的评论
        Set<String> keys = stringRedisTemplate.keys(LIKE_COMMENT_KEY + "*");
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
                    String commentId = likeKey.split(":")[2];
                    crcUserCommentMapper.updateLikes(commentId, count);
                }
            }
        }
    }


}
