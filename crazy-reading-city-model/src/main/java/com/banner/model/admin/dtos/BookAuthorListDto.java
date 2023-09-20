package com.banner.model.admin.dtos;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author rjj
 * @date 2023/9/20 - 8:19
 */
@Data
public class BookAuthorListDto {
    private Long id;

    /**
     * 作者姓名
     */
    private String authorName;

    /**
     * 作者国籍
     */
    private String nationality;

    private Integer bookCount;
}
