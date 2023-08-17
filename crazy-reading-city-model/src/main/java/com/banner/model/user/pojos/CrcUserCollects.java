package com.banner.model.user.pojos;

import java.time.LocalDate;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import java.time.LocalDateTime;

import lombok.Builder;
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
public class CrcUserCollects implements Serializable {


    /**
     * id
     */
    private Long id;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 收藏夹名
     */
    private String collectName;

    /**
     * 0公开;1私密
     */
    private Integer status;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;


}
