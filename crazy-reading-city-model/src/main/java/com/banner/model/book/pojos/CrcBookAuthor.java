package com.banner.model.book.pojos;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;

/**
 * <p>
 * 
 * </p>
 *
 * @author rjj
 * @since 2023-07-27
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class CrcBookAuthor implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @ApiModelProperty(value = "添加作者时为空")
    private Long id;

    /**
     * 作者姓名
     */
    @NotBlank
    private String authorName;

    /**
     * 作者国籍
     */
    private String nationality;

    /**
     * 作者简介
     */
    private String intro;


}
