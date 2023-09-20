package com.banner.user.service;

import com.banner.model.common.dtos.PageDto;
import com.banner.model.common.dtos.ResponseResult;
import com.banner.model.user.dtos.PlanConclusionDto;
import com.banner.model.user.pojos.CrcUserPlan;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author rjj
 * @since 2023-08-11
 */
public interface CrcUserPlanService extends IService<CrcUserPlan> {

    ResponseResult getPlans(PageDto bookPlanPageDto);

    ResponseResult addPlanConclusion(PlanConclusionDto planConclusionDto);
}
