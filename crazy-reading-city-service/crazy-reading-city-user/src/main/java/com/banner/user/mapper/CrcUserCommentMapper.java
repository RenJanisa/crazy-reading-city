package com.banner.user.mapper;

import com.banner.model.user.dtos.GetUserCommentDto;
import com.banner.model.user.pojos.CrcUserComment;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

import java.util.List;
import java.util.Set;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author rjj
 * @since 2023-08-01
 */
public interface CrcUserCommentMapper extends BaseMapper<CrcUserComment> {

    List<GetUserCommentDto> listById(@Param("objId") Long objId);

    @Update("update crc_user_comment set likes = likes+#{count} where id = #{commentId}")
    void updateLikes(@Param("commentId") String commentId,@Param("count") Integer count);
}
