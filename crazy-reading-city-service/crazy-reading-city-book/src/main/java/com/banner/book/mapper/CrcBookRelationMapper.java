package com.banner.book.mapper;

import com.banner.model.book.dtos.BookTypesSimpleDto;
import com.banner.model.book.dtos.CrcBookInfoDto;
import com.banner.model.book.pojos.CrcBookRelation;
import com.banner.model.search.dtos.CrcBookSearchDto;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author rjj
 * @since 2023-07-31
 */
public interface CrcBookRelationMapper extends BaseMapper<CrcBookRelation> {


    List<CrcBookSearchDto> getTypeBook(@Param("typeId") String typeId, @Param("page") int page,@Param("pageSize") Integer pageSize);

    List<BookTypesSimpleDto> getBookTypes(@Param("bookId") String bookId);
}
