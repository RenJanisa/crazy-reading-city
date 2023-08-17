package com.banner.user.mapper;

import com.banner.model.user.dtos.GetUserFollowDto;
import com.banner.model.user.pojos.CrcUserFollow;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author rjj
 * @since 2023-07-31
 */
public interface CrcUserFollowMapper extends BaseMapper<CrcUserFollow> {

    List<GetUserFollowDto> getMeTo(@Param("userId") String userId);

    List<GetUserFollowDto> getToMe(@Param("userId") String userId);


}
