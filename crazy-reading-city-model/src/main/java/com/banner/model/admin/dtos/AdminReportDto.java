package com.banner.model.admin.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author rjj
 * @date 2023/8/24 - 10:20
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdminReportDto {

    private Integer bookCount;
    private Integer bookExcerptCount;
    private Integer bookAuthorCount;
    private Integer userCount;
    private Integer userCommentCount;
    private Integer userPlanCount;

}
