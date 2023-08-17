package com.banner.user.controller;


import com.banner.common.utils.AppJwtUtil;
import com.banner.common.utils.ThreadLocalUtil;
import com.banner.model.common.dtos.ResponseResult;
import com.banner.model.user.dtos.EnrollDto;
import com.banner.model.user.dtos.LoginDto;
import com.banner.model.user.dtos.UpdateDto;
import com.banner.model.user.pojos.CrcUser;
import com.banner.user.service.CrcUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.http.HttpHeaders;
import org.apache.ibatis.annotations.Delete;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author rjj
 * @since 2023-07-20
 */
@RestController
@RequestMapping("/crc-user")
@Api(value = "用户端接口", tags = "用户端接口")
public class CrcUserController {

    @Resource
    private CrcUserService crcUserService;

    @ApiOperation("用户登录")
    @PostMapping("/login")
    public ResponseResult login(@RequestBody @Validated LoginDto loginDto){
        return crcUserService.login(loginDto);
    }

    @ApiOperation("发送邮件验证码")
    @GetMapping("/send-code")
    public ResponseResult sendCode(String email){
        return crcUserService.sendCode(email);
    }

    @PostMapping("/enroll")
    @ApiOperation("用户注册")
    public ResponseResult enroll(@RequestBody @Validated EnrollDto enrollDto){
        return crcUserService.enroll(enrollDto);
    }

    @ApiOperation("用户信息查询")
    @GetMapping("/get")
    public ResponseResult get(Long userId){
        return crcUserService.get(userId);
    }

    @ApiOperation("用户更新")
    @PostMapping("/update")
    public ResponseResult update(@RequestBody @Validated UpdateDto updateDto){

        return crcUserService.updateInfo(updateDto);
    }

    @ApiOperation("用户注销")
    @DeleteMapping("/logout")
    public ResponseResult logout(){
        return ResponseResult.okResult(crcUserService.removeById(ThreadLocalUtil.getId()) ? "注销成功":"注销失败");
    }

    @ApiOperation("上传图片")
    @PostMapping("/upload-img")
    public ResponseResult uploadImg(MultipartFile multipartFile){
        return crcUserService.uploadImg(multipartFile);
    }

}
