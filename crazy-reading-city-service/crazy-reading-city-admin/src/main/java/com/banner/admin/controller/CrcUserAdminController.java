package com.banner.admin.controller;

import com.banner.admin.service.CrcAdminUserService;
import com.banner.model.common.dtos.PageDto;
import com.banner.model.common.dtos.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author rjj
 * @date 2023/9/20 - 8:42
 */
@RestController
@RequestMapping("/crc-user-admin")
@Api(tags = "后台用户接口")
public class CrcUserAdminController {

    @Resource
    private CrcAdminUserService crcAdminUserService;

    @ApiOperation("查询用户列表")
    @PostMapping("/user-list")
    public ResponseResult getUserList(PageDto pageDto){
        return crcAdminUserService.getUserList(pageDto);
    }


}
