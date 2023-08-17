package com.banner.user.mapper;

import com.banner.model.user.dtos.GetUserReplyDto;
import com.banner.model.user.dtos.GetUserReplyPlusDto;
import com.banner.model.user.pojos.CrcUserReply;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author rjj
 * @since 2023-08-01
 */
public interface CrcUserReplyMapper extends BaseMapper<CrcUserReply> {

    List<GetUserReplyDto> listById(@Param("commentId") Long commentId);

    List<GetUserReplyPlusDto> listPlusById(@Param("commentId") Long commentId);

    @Update("update crc_user_reply set likes = likes+#{count} where id = #{replyId}")
    void updateLikes(@Param("replyId") String replyId,@Param("count") Integer count);
}
