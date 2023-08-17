package com.banner.model.user.dtos;

import lombok.Data;

import javax.validation.constraints.Size;

/**
 * @author rjj
 * @date 2023/7/26 - 10:08
 */
@Data
public class EnrollDto extends LoginDto{
    @Size(min = 6,max = 6,message = "请输入6位验证码")
    private String code;
}
