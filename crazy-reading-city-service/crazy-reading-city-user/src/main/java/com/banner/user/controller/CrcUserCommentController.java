package com.banner.user.controller;


import com.banner.model.common.dtos.ResponseResult;
import com.banner.model.user.dtos.AddCRDto;
import com.banner.user.service.CrcUserCommentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
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
 * @since 2023-08-01
 */
@Api(value = "crc-user-comment",tags = "用户评论接口")
@RestController
@RequestMapping("/crc-user-comment")
public class CrcUserCommentController {

    @Resource
    private CrcUserCommentService crcUserCommentService;

    @ApiOperation(value = "发布评论")
    @PostMapping("/add")
    public ResponseResult addComment(@RequestBody @Validated AddCRDto addCRDto){
        return crcUserCommentService.addComment(addCRDto);
    }

    @ApiOperation(value = "查看评论")
    @GetMapping("/get")
    @ApiImplicitParam(name = "objId",value = "书籍或摘录id")
    public ResponseResult getComment(Long objId){
        return crcUserCommentService.getComment(objId);
    }

    @ApiOperation("点赞/取消")
    @PostMapping("/like")
    public ResponseResult like(String commentId,String userId){
        return crcUserCommentService.like(commentId,userId);
    }

}
