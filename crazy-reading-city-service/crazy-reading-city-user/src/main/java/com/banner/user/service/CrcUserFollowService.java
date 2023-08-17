package com.banner.user.service;

import com.banner.model.common.dtos.ResponseResult;
import com.banner.model.user.dtos.DoFollowDto;
import com.banner.model.user.pojos.CrcUserFollow;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author rjj
 * @since 2023-07-31
 */
public interface CrcUserFollowService extends IService<CrcUserFollow> {

    ResponseResult doFollow(DoFollowDto doFollowDto);

    ResponseResult getFollow(String userId, Integer flag);

}
