package com.banner.user.controller;


import com.banner.model.common.dtos.ResponseResult;
import com.banner.model.user.pojos.CrcUserPlanDetail;
import com.banner.user.service.CrcUserPlanDetailService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author rjj
 * @since 2023-08-11
 */
@Api(tags = "分计划接口")
@RestController
@RequestMapping("/crc-user-plan-detail")
public class CrcUserPlanDetailController {

    @Resource
    private CrcUserPlanDetailService crcUserPlanDetailService;

    @ApiOperation("添加分计划")
    @PostMapping("/add")
    public ResponseResult addPlanDetail(@RequestBody @Validated CrcUserPlanDetail crcUserPlanDetail){
        return crcUserPlanDetailService.addPlanDetail(crcUserPlanDetail);
    }

    @ApiOperation("查看计划详情")
    @GetMapping("/get")
    public ResponseResult getPlanDetail(String planId){
        return crcUserPlanDetailService.getPlanDetail(planId);
    }

}
