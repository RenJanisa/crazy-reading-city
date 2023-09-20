package com.banner.model.book.dtos;

import com.banner.model.book.pojos.CrcBook;
import com.banner.model.book.pojos.CrcBookAuthor;
import lombok.Data;

import java.util.List;

/**
 * @author rjj
 * @date 2023/7/27 - 17:36
 */
@Data
public class CrcBookInfoDto extends CrcBook {

    /**
     * 作者姓名
     */
    private String authorName;

    /**
     * 作者国籍
     */
    private String nationality;

    /**
     * 作者简介
     */
    private String authorIntro;

   private List<BookTypesSimpleDto> bookTypes;

}
