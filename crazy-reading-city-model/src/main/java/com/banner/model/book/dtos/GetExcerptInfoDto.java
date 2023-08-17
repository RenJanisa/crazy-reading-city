package com.banner.model.book.dtos;

import com.banner.model.book.pojos.CrcBookExcerpt;
import lombok.Data;

/**
 * @author rjj
 * @date 2023/8/1 - 9:41
 */
@Data
public class GetExcerptInfoDto extends CrcBookExcerpt {
    private String isLike;
    private Long uv;
}
