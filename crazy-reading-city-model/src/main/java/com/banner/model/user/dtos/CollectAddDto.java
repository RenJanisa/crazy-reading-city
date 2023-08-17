package com.banner.model.user.dtos;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author rjj
 * @date 2023/7/31 - 15:29
 */
@Data
public class CollectAddDto {
    /**
     * 用户id
     */
    private Long userId;

    /**
     * 收藏夹名
     */
    @NotBlank(message = "收藏夹名不为空")
    private String collectName;

    /**
     * 0公开;1私密
     */
    private Integer status;

}
