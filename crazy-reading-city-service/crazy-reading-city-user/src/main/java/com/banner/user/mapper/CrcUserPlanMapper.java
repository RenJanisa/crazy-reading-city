package com.banner.user.mapper;

import com.banner.model.user.dtos.UserPlansDto;
import com.banner.model.user.pojos.CrcUserPlan;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
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
public interface CrcUserPlanMapper extends BaseMapper<CrcUserPlan> {

    @Select("SELECT * FROM crc_user_plan WHERE user_id = #{userId} LIMIT #{page},#{pageSize}")
    List<UserPlansDto> getPlansPage(@Param("userId") String userId,
                                    @Param("page") int page,
                                    @Param("pageSize") Integer pageSize);
}
