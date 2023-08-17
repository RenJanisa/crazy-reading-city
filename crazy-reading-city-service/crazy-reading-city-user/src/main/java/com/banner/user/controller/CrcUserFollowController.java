package com.banner.user.controller;


import com.banner.model.common.dtos.ResponseResult;
import com.banner.model.user.dtos.DoFollowDto;
import com.banner.user.mapper.CrcUserFollowMapper;
import com.banner.user.service.CrcUserFollowService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author rjj
 * @since 2023-07-31
 */
@Api(tags = "用户关注接口")
@RestController
@RequestMapping("/crc-user-follow")
public class CrcUserFollowController {


    @Resource
    private CrcUserFollowService crcUserFollowService;


    @ApiOperation("关注或取消")
    @PostMapping("/do")
    public ResponseResult doFollow(DoFollowDto doFollowDto){

        return crcUserFollowService.doFollow(doFollowDto);
    }

    @ApiOperation("查看关注或粉丝")
    @GetMapping("/get")
    public ResponseResult getFollow(String userId, Integer flag){
        return crcUserFollowService.getFollow(userId,flag);
    }



}
