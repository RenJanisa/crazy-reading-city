package com.banner.model.user.dtos;

import com.banner.model.book.dtos.BookSimpleDto;
import com.banner.model.user.pojos.CrcUserPlan;
import lombok.Data;

/**
 * @author rjj
 * @date 2023/9/1 - 17:28
 */
@Data
public class UserPlansDto extends CrcUserPlan {

    private BookSimpleDto bookInfo;

}
