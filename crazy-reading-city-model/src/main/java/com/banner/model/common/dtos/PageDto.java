package com.banner.model.common.dtos;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author rjj
 * @date 2023/8/13 - 10:34
 */
@Data
public class PageDto {

    private String condition;
    @NotNull
    private Integer page;
    @NotNull
    private Integer pageSize;

}
