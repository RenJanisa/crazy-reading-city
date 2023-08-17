package com.banner.model.book.dtos;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author rjj
 * @date 2023/8/13 - 10:34
 */
@Data
public class TypeBookDto {

    private String typeId;
    @NotNull
    private Integer page;
    @NotNull
    private Integer pageSize;

}
