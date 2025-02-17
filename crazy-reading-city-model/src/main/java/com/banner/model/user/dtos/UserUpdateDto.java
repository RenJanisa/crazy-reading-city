package com.banner.model.user.dtos;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.time.LocalDate;

/**
 * @author rjj
 * @date 2023/7/31 - 9:53
 */
@Data
public class UserUpdateDto {

    private Long id;

    /**
     * 用户名
     */
    @NotBlank
    private String userName;


    @NotBlank
    private String avatar;

    /**
     * 性别（0女，1男）
     */
    private Integer gender;

    /**
     * 学校
     */
    private String school;

    /**
     * 出生日期
     */
    private LocalDate birth;

    /**
     * 自我介绍
     */
    private String description;



}
