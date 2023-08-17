package com.banner.user.service;

import com.banner.model.common.dtos.ResponseResult;
import com.banner.model.user.pojos.CrcUserPlanDetail;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author rjj
 * @since 2023-08-11
 */
public interface CrcUserPlanDetailService extends IService<CrcUserPlanDetail> {

    ResponseResult addPlanDetail(CrcUserPlanDetail crcUserPlanDetail);

    ResponseResult getPlanDetail(String planId);
}
