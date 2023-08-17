package com.banner.model.book.pojos;

import java.time.LocalDate;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import java.time.LocalDateTime;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

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
public class CrcBookCollect implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @ApiModelProperty(hidden = true)
    private Long id;

    @NotNull
    @ApiModelProperty(value = "收藏夹id")
    private Long collectId;

    /**
     * 收藏对象id
     */
    @NotNull
    @ApiModelProperty(value = "收藏对象id")
    private Long objId;

    /**
     * 0书籍,1摘录
     */
    @NotNull
    @ApiModelProperty(value = "0书籍,1摘录")
    private Integer flag;

    /**
     * 收藏时间
     */
    @TableField(fill = FieldFill.INSERT)
    @ApiModelProperty(hidden = true)
    private LocalDateTime createTime;


}
