package com.banner.model.user.dtos;

import com.banner.model.user.pojos.CrcUserReply;
import lombok.Data;

/**
 * @author rjj
 * @date 2023/8/1 - 15:39
 */
@Data
public class GetUserReplyDto extends CrcUserReply {
    private String userName;
    private String avatar;
    private String isLike;
}
