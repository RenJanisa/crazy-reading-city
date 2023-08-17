package com.banner.book.mapper;

import com.banner.model.book.dtos.BookSimpleDto;
import com.banner.model.book.dtos.CrcBookInfoDto;
import com.banner.model.book.pojos.CrcBook;
import com.banner.model.search.dtos.CrcBookSearchDto;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author rjj
 * @since 2023-07-27
 */
public interface CrcBookMapper extends BaseMapper<CrcBook> {

    CrcBookInfoDto getBookInfo(@Param("bookId") String bookId);


    BookSimpleDto getNameAndAuthor(@Param("objId") Long objId);

    @Update("update crc_book set collect = collect+1 where id = #{objId}")
    void updateCollect(Long objId);

    @Select("select cb.id,cb.book_name,cb.img from crc_book cb")
    List<CrcBookSearchDto> getBookSearchList();

    List<CrcBookSearchDto> getRecommendBook();
}
