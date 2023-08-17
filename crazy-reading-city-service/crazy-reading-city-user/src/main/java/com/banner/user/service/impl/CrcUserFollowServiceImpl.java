package com.banner.user.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.banner.common.utils.ThreadLocalUtil;
import com.banner.model.common.dtos.ResponseResult;
import com.banner.model.common.enums.AppHttpCodeEnum;
import com.banner.model.user.dtos.DoFollowDto;
import com.banner.model.user.dtos.GetUserFollowDto;
import com.banner.model.user.pojos.CrcUser;
import com.banner.model.user.pojos.CrcUserFollow;
import com.banner.user.mapper.CrcUserFollowMapper;
import com.banner.user.mapper.CrcUserMapper;
import com.banner.user.service.CrcUserFollowService;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

import static com.banner.common.constants.LikeOrNotConstant.IS_LIKE;
import static com.banner.common.constants.LikeOrNotConstant.IS_NOT_LIKE;
import static com.banner.common.constants.RedisConstants.LIKE_COMMENT_KEY;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author rjj
 * @since 2023-07-31
 */
@Service
public class CrcUserFollowServiceImpl extends ServiceImpl<CrcUserFollowMapper, CrcUserFollow> implements CrcUserFollowService {

    @Override
    public ResponseResult doFollow(DoFollowDto doFollowDto) {

        if (StrUtil.isNotBlank(doFollowDto.getIsHave())) {
            //已关注,取消关注
            boolean b = this.removeById(doFollowDto.getIsHave());
            return b?ResponseResult.okResult(200,"取消成功")
                    :ResponseResult.errorResult(500,"取消失败");
        }

        if (doFollowDto.getUserId() == null) doFollowDto.setUserId(ThreadLocalUtil.getId().toString());

        //未关注,去关注
        CrcUserFollow crcUserFollow = BeanUtil.copyProperties(doFollowDto, CrcUserFollow.class);
        crcUserFollow.setId(IdWorker.getId());
        boolean save = this.save(crcUserFollow);

        return save?ResponseResult.okResult(crcUserFollow.getId())
                :ResponseResult.errorResult(500,"关注失败");

    }

    @Resource
    private CrcUserFollowMapper crcUserFollowMapper;

    @Override
    public ResponseResult getFollow(String userId, Integer flag) {
        List<GetUserFollowDto> getUserFollowDtos;
        if (flag == 0){
            //查询关注
            getUserFollowDtos = crcUserFollowMapper.getMeTo(userId);
        }else {
            getUserFollowDtos = crcUserFollowMapper.getToMe(userId);
        }

        return ResponseResult.okResult(getUserFollowDtos);
    }



}
