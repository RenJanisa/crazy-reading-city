package com.banner.model.user.dtos;

import lombok.Data;

/**
 * @author rjj
 * @date 2023/8/1 - 17:13
 */
@Data
public class GetUserReplyPlusDto extends GetUserReplyDto{

    private String isLike;
    private String replierName;
    private String replierAvatar;

}
