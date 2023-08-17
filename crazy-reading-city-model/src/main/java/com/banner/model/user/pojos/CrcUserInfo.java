package com.banner.model.user.pojos;

import java.time.LocalDate;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 
 * </p>
 *
 * @author rjj
 * @since 2023-07-31
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class CrcUserInfo implements Serializable {


    /**
     * id
     */
    private Long id;

    /**
     * 关联用户
     */
    private Long userId;

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
