package com.banner.model.search.dtos;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author rjj
 * @date 2023/8/9 - 20:40
 */
@Data
public class GetBookSearchDto {

    @NotBlank
    private String bookName;

    @ApiModelProperty(value = "第几页")
    @NotNull
    private Integer page;

    @NotNull
    @ApiModelProperty(value = "一页多少条")
    private Integer pageSize;

}
