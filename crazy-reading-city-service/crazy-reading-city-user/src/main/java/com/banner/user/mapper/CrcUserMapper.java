package com.banner.user.mapper;

import com.banner.model.admin.dtos.AdminUserListDto;
import com.banner.model.user.dtos.SimpleUserDto;
import com.banner.model.user.dtos.UserUpdateDto;
import com.banner.model.user.pojos.CrcUser;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author rjj
 * @since 2023-07-20
 */
public interface CrcUserMapper extends BaseMapper<CrcUser> {

    UserUpdateDto getUserInfo(@Param("id") Long userId);

    @Select("select cu.user_name,cu.avatar from crc_user cu where cu.id = #{userId}")
    SimpleUserDto getSimple(@Param("userId") Long userId);

    List<AdminUserListDto> listUser(@Param("page") int page, @Param("pageSize") Integer pageSize);

    List<AdminUserListDto> listUserByName(@Param("userName") String userName,
                                          @Param("page") int page,
                                          @Param("pageSize") Integer pageSize);
}
