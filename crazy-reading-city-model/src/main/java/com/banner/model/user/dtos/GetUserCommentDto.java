package com.banner.model.user.dtos;

import com.banner.model.user.pojos.CrcUserComment;
import com.banner.model.user.pojos.CrcUserReply;
import lombok.Data;

import java.util.List;

/**
 * @author rjj
 * @date 2023/8/1 - 15:31
 */
@Data
public class GetUserCommentDto extends CrcUserComment {

    private String userName;
    private String avatar;
    private String isLike;

    private List<GetUserReplyDto> replies;
    private List<GetUserReplyPlusDto> replyPlus;

}
