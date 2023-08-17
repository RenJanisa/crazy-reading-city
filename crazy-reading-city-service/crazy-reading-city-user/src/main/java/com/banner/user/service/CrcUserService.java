package com.banner.user.service;

import com.banner.model.common.dtos.ResponseResult;
import com.banner.model.user.dtos.EnrollDto;
import com.banner.model.user.dtos.LoginDto;
import com.banner.model.user.dtos.SimpleUserDto;
import com.banner.model.user.dtos.UpdateDto;
import com.banner.model.user.pojos.CrcUser;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.multipart.MultipartFile;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author rjj
 * @since 2023-07-20
 */
public interface CrcUserService extends IService<CrcUser> {

    ResponseResult login(LoginDto loginDto);

    void sendEmail (String email,String content);

    ResponseResult sendCode(String email);

    ResponseResult enroll(EnrollDto enrollDto);

    ResponseResult updateInfo(UpdateDto updateDto);

    ResponseResult get(Long userId);

    SimpleUserDto getSimple(Long userId);

    ResponseResult uploadImg(MultipartFile multipartFile);
}
