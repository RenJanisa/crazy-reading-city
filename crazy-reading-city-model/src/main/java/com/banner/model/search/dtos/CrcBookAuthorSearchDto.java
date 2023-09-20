package com.banner.model.search.dtos;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author rjj
 * @date 2023/8/17 - 16:53
 */
@Data
public class CrcBookAuthorSearchDto {
    private Long id;

    /**
     * 作者姓名
     */
    private String authorName;

    /**
     * 作者国籍
     */
    private String nationality;
}
