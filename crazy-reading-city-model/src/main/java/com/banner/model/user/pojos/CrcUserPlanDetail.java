package com.banner.model.user.pojos;

import java.time.LocalDateTime;
import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;

/**
 * <p>
 * 
 * </p>
 *
 * @author rjj
 * @since 2023-08-11
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class CrcUserPlanDetail implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @ApiModelProperty(hidden = true)
    private Long id;

    /**
     * 计划id
     */
    @NotNull
    private Long planId;

    /**
     * 分计划状态:0未完成,1完成
     */
    @ApiModelProperty(hidden = true)
    private Integer status;

    /**
     * 分计划开始日期
     */
    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime beginTime;

    /**
     * 分计划结束日期
     */
    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime endTime;

    /**
     * 分计划内容
     */
    @ApiModelProperty(value = "分计划内容")
    @NotNull
    private String content;

    /**
     * 分计划总结
     */
    @ApiModelProperty(hidden = true)
    private String conclusion;

    @ApiModelProperty(value = "第几个分计划")
    private Integer orders;


}
