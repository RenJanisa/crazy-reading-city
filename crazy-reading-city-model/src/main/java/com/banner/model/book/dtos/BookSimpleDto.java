package com.banner.model.book.dtos;

import lombok.Data;

/**
 * @author rjj
 * @date 2023/7/31 - 16:31
 */
@Data
public class BookSimpleDto {

    private String name;


    private Long authorId;

    private String author;

    /**
     * 作者国籍
     */
    private String address;

}
