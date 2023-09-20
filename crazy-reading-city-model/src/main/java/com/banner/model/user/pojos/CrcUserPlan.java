package com.banner.model.user.pojos;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * <p>
 * 
 * </p>
 *
 * @author rjj
 * @since 2023-08-11
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class CrcUserPlan implements Serializable {


    /**
     * id
     */
    @ApiModelProperty(hidden = true)
    private Long id;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 书籍id
     */
    @NotNull
    private Long bookId;

    /**
     * 计划标题
     */
    @Size(max = 255)
    private String title;

    /**
     * 计划状态:0未完成,1完成
     */
    @ApiModelProperty(hidden = true)
    private int status;

    /**
     * 计划总结
     */
    @ApiModelProperty(hidden = true)
    private String conclusion;

    /**
     * 0公开,1私密
     */
    @ApiModelProperty(value = "0公开,1私密")
    private Integer flag;


}
