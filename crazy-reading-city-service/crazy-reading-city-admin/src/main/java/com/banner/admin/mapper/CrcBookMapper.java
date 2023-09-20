package com.banner.admin.mapper;

import com.banner.model.admin.dtos.AdminBookListDto;
import com.banner.model.book.dtos.BookSimpleDto;
import com.banner.model.book.dtos.CrcBookInfoDto;
import com.banner.model.book.pojos.CrcBook;
import com.banner.model.search.dtos.CrcBookSearchDto;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author rjj
 * @since 2023-07-27
 */
public interface CrcBookMapper extends BaseMapper<CrcBook> {


    @Select("SELECT cb.id,cb.book_name,cb.img,cb.collect,cb.pages,cb.intro,cb.publish_time,cb.author_id,cba.author_name FROM crc_book cb Inner Join crc_book_author cba on cb.author_id=cba.id WHERE book_name like #{bookName} LIMIT #{page},#{pageSize}")
    List<AdminBookListDto> listBookByName(@Param("bookName") String bookName,
                                    @Param("page") int page,
                                    @Param("pageSize") Integer pageSize);

    @Select("SELECT cb.id,cb.book_name,cb.img,cb.collect,cb.pages,cb.intro,cb.publish_time,cb.author_id,cba.author_name FROM crc_book cb Inner Join crc_book_author cba on cb.author_id=cba.id LIMIT #{page},#{pageSize}")
    List<AdminBookListDto> listBook(int page, Integer pageSize);
}
