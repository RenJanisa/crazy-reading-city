package com.banner.user.mapper;

import com.banner.model.user.dtos.SimpleUserDto;
import com.banner.model.user.dtos.UpdateDto;
import com.banner.model.user.pojos.CrcUser;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import javax.validation.constraints.Size;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author rjj
 * @since 2023-07-20
 */
public interface CrcUserMapper extends BaseMapper<CrcUser> {

    UpdateDto getUserInfo(@Param("id") Long userId);

    @Select("select cu.user_name,cu.avatar from crc_user cu where cu.id = #{userId}")
    SimpleUserDto getSimple(@Param("userId") Long userId);
}
