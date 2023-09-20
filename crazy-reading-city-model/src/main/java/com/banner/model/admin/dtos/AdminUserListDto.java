package com.banner.model.admin.dtos;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * @author rjj
 * @date 2023/9/20 - 8:57
 */
@Data
public class AdminUserListDto {

    private Long id;

    private String userName;

    private String email;

    private Integer gender;

    private String school;

    private Integer planCount;

    private Integer excerptCount;

    private LocalDate birth;

    private LocalDateTime createTime;


}
