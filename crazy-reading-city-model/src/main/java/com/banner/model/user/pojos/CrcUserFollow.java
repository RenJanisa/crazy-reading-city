package com.banner.model.user.pojos;

import java.time.LocalDate;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import java.time.LocalDateTime;

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
public class CrcUserFollow implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    private Long id;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 关注对象id
     */
    private Long followId;

    /**
     * 关注时间
     */
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;


}
