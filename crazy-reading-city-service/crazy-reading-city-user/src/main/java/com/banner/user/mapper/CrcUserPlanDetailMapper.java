package com.banner.user.mapper;

import com.banner.model.user.dtos.PlanOutDateDto;
import com.banner.model.user.pojos.CrcUserPlanDetail;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author rjj
 * @since 2023-08-11
 */
public interface CrcUserPlanDetailMapper extends BaseMapper<CrcUserPlanDetail> {

    @Select("select orders from crc_user_plan_detail where plan_id = #{planId}")
    List<Integer> getPlanDetailOrders(Long planId);

    List<PlanOutDateDto> getOutDatePlan();
}
