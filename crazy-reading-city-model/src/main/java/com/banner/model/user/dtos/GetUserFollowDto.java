package com.banner.model.user.dtos;

import com.banner.model.user.pojos.CrcUserFollow;
import lombok.Data;

/**
 * @author rjj
 * @date 2023/8/3 - 20:02
 */
@Data
public class GetUserFollowDto extends CrcUserFollow {

    private String userName;
    private String avatar;

}
