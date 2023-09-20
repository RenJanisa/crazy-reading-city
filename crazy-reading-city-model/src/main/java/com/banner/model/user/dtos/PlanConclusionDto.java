package com.banner.model.user.dtos;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * @author rjj
 * @date 2023/9/9 - 9:56
 */
@Data
public class PlanConclusionDto {

    @NotBlank
    @ApiModelProperty(value = "计划或分计划id")
    private String objId;

    @NotBlank
    private String conclusion;

    @ApiModelProperty(value = "0:计划,1:分计划")
    @NotNull
    private Integer flag;
}
