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
public class CrcBook implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(hidden = true)
    private Long id;

    @NotBlank
    private String bookName;

    @NotBlank
    private Long authorId;

    private String img;

    private String description;

    /**
     * 页数
     */
    private Integer pages;

    /**
     * 书籍目录
     */
    private String catalogue;

    /**
     * 书籍简介
     */
    private String intro;

    private String publishTime;


}
