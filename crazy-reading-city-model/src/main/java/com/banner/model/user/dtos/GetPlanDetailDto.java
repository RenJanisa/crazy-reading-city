package com.banner.model.user.dtos;

import com.banner.model.user.pojos.CrcUserPlan;
import com.banner.model.user.pojos.CrcUserPlanDetail;
import lombok.Data;

import java.util.List;

/**
 * @author rjj
 * @date 2023/8/11 - 20:41
 */
@Data
public class GetPlanDetailDto extends CrcUserPlan {

    private String bookName;
    private String author;
    private String nationality;
    private List<CrcUserPlanDetail> crcUserPlanDetails;
}
