package com.banner.model.user.pojos;

import java.time.LocalDate;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import java.time.LocalDateTime;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * <p>
 * 
 * </p>
 *
 * @author rjj
 * @since 2023-07-20
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class CrcUser implements Serializable {


    private Long id;

    /**
     * 用户名
     */
    @NotBlank
    private String userName;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 密码
     */
    @Size(min = 6,max = 12,message = "密码在6~12位之间")
    private String password;

    /**
     * 盐(加密密码)
     */
    @ApiModelProperty(value = "加密盐")
    private String salt;

    /**
     * 性别(0女,1男)
     */
    @NotBlank
    private String avatar;

    /**
     * 身份区别(0管理员,1用户)
     */
    private Integer flag;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;


}
