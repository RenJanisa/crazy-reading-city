package com.banner.user.controller;

import com.banner.common.utils.ThreadLocalUtil;
import com.banner.model.common.dtos.PageDto;
import com.banner.model.common.dtos.ResponseResult;
import com.banner.model.user.dtos.PlanConclusionDto;
import com.banner.model.user.pojos.CrcUserPlan;
import com.banner.user.service.CrcUserPlanService;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author rjj
 * @date 2023/8/11 - 9:56
 */
@Api(tags = "读书计划接口")
@RestController
@RequestMapping("/crc-user-plan")
public class CrcUserPlanController {

    @Resource
    private CrcUserPlanService crcUserPlanService;

    @ApiOperation("添加读书计划")
    @PostMapping("/add")
    public ResponseResult addPlan(@RequestBody @Validated CrcUserPlan crcUserPlan){
        if (crcUserPlan.getUserId() == null) crcUserPlan.setUserId(ThreadLocalUtil.getId());
        if (crcUserPlan.getFlag() == null) crcUserPlan.setFlag(0);
        long planId = IdWorker.getId();
        crcUserPlan.setId(planId);
        crcUserPlan.setStatus(0);
        boolean save = crcUserPlanService.save(crcUserPlan);
        return save? ResponseResult.okResult(planId)
                :ResponseResult.errorResult(500,"添加失败");
    }

    @ApiOperation("添加计划总结")
    @PostMapping("/add-conclusion")
    public ResponseResult addPlanConclusion(@RequestBody @Validated PlanConclusionDto planConclusionDto){
        return crcUserPlanService.addPlanConclusion(planConclusionDto);
    }



    @ApiOperation("查看计划列表")
    @PostMapping("/get-list")
    public ResponseResult getPlans(@RequestBody @Validated PageDto bookPlanPageDto){
        return crcUserPlanService.getPlans(bookPlanPageDto);
    }



}
