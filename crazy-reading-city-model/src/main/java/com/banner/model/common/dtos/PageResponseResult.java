package com.banner.model.common.dtos;

import com.google.errorprone.annotations.NoAllocation;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PageResponseResult extends ResponseResult implements Serializable {

    private Integer currentPage;
    private Integer pageSize;
    private Integer total;

}
