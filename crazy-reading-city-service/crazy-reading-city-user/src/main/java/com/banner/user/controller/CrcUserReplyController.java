package com.banner.user.controller;


import com.banner.model.common.dtos.ResponseResult;
import com.banner.model.user.dtos.AddCRDto;
import com.banner.model.user.pojos.CrcUserReply;
import com.banner.user.mapper.CrcUserReplyMapper;
import com.banner.user.service.CrcUserReplyService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.checkerframework.checker.units.qual.C;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author rjj
 * @since 2023-08-01
 */
@Api(value = "crc-user-reply", tags = "用户回复接口")
@RestController
@RequestMapping("/crc-user-reply")
public class CrcUserReplyController {

    @Resource
    private CrcUserReplyService crcUserReplyService;

    @ApiOperation(value = "添加回复")
    @PostMapping("/add")
    public ResponseResult addReply(@RequestBody @Validated AddCRDto addCRDto){

        return crcUserReplyService.addReply(addCRDto);
    }

    @ApiOperation("点赞/取消")
    @PostMapping("/like")
    public ResponseResult like(String replyId,String userId){
        return crcUserReplyService.like(replyId,userId);
    }



}
