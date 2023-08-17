package com.banner.model.book.dtos;

import com.banner.model.book.pojos.CrcBookCollect;
import lombok.Data;

/**
 * @author rjj
 * @date 2023/7/31 - 16:19
 */
@Data
public class CrcCollectsDto extends CrcBookCollect {

    //书籍名或摘录标题
    private String name;
    private String authorId;
    //作者名
    private String author;

    private String address;

}
