package com.banner.model.admin.dtos;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author rjj
 * @date 2023/8/24 - 17:01
 */
@Data
public class AdminBookListDto {
    private Long id;

    private String bookName;

    private String img;

    private Integer collect;

    /**
     * 页数
     */
    private Integer pages;

    /**
     * 书籍简介
     */
    private String intro;

    private String publishTime;

    private Long authorId;
    /**
     * 作者姓名
     */
    @NotBlank
    private String authorName;

}
