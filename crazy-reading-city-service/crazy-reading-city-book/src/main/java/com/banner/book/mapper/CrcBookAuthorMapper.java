package com.banner.book.mapper;

import com.banner.model.book.pojos.CrcBookAuthor;
import com.banner.model.search.dtos.CrcBookAuthorSearchDto;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

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
public interface CrcBookAuthorMapper extends BaseMapper<CrcBookAuthor> {

    @Select("select id,author_name,nationality from crc_book_author")
    List<CrcBookAuthorSearchDto> getBookAuthorList();

    List<CrcBookAuthorSearchDto> getBookAuthorListByCondition(@Param("nationality") String nationality,
                                                              @Param("page") Integer page,
                                                              @Param("pageSize") Integer pageSize);

    @Select("SELECT nationality,COUNT(*) count FROM crc_book_author GROUP BY nationality")
    List<Map> getBookAuthorNationality();
}
