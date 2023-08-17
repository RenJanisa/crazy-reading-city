package com.banner.book.mapper;

import com.banner.model.book.dtos.BookSimpleDto;
import com.banner.model.book.dtos.GetBookExcerptDto;
import com.banner.model.book.pojos.CrcBookExcerpt;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.swagger.models.auth.In;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author rjj
 * @since 2023-07-28
 */
public interface CrcBookExcerptMapper extends BaseMapper<CrcBookExcerpt> {

    @Update("update crc_book_excerpt set status = #{status} where id = #{id}")
    void updateStatus(Long id, Integer status);

    BookSimpleDto getNameAndAuthor(@Param("objId") Long objId);

    @Update("update crc_book_excerpt set collect = collect+1 where id = #{objId}")
    void updateCollect(Long objId);

    List<GetBookExcerptDto> getBookExcerptList(@Param("bookId") String bookId);

    @Update("update crc_book_excerpt set likes = likes+#{count} where id = #{excerptId}")
    void updateLikes(@Param("excerptId") String excerptId, @Param("count") int count);
}
