package com.banner.model.user.dtos;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * @author rjj
 * @date 2023/8/1 - 15:17
 */

@Data
public class AddCRDto {

    @NotNull
    private Long objId;

    @NotNull
    private Long userId;

    /**
     * 评论内容
     */
    @Size(max = 2048,message = "0到2048位之间")
    private String content;

}
