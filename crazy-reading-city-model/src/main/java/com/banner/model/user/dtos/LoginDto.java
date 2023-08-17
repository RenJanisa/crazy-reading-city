package com.banner.model.user.dtos;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * @author rjj
 * @date 2023/7/20 - 21:50
 */
@Data
public class LoginDto {

    @Email(message = "邮箱格式不正确")
    private String email;
    @Size(min = 6,max = 12,message = "密码在6~12位之间")
    private String password;

}
