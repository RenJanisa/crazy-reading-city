package com.banner.model.book.dtos;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author rjj
 * @date 2023/8/8 - 9:13
 */
@Data
public class GetBookMessageDto {
    @NotBlank
    private String bookId;
    private Integer page;
    private Integer rows;
}
