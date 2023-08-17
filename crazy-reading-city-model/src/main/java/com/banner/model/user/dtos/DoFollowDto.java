package com.banner.model.user.dtos;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author rjj
 * @date 2023/8/3 - 19:38
 */
@Data
public class DoFollowDto {
    private String userId;
    @NotBlank(message = "关注目标不为空")
    @ApiModelProperty(value = "关注目标id")
    private String followId;
    @NotNull(message = "状态不为空")
    private String isHave;
}
