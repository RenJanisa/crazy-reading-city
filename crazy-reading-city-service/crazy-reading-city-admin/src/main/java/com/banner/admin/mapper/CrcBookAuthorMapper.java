package com.banner.admin.mapper;

import com.banner.model.admin.dtos.BookAuthorListDto;
import com.banner.model.book.pojos.CrcBookAuthor;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author rjj
 * @since 2023-07-27
 */
public interface CrcBookAuthorMapper extends BaseMapper<CrcBookAuthor> {

    List<BookAuthorListDto> listAuthorByName(@Param("authorName") String authorName,
                                             @Param("page") int page,
                                             @Param("pageSize") Integer pageSize);
    List<BookAuthorListDto> listAuthorByNationality(@Param("nationality") String nationality,
                                                @Param("page") int page,
                                                @Param("pageSize") Integer pageSize);
    List<BookAuthorListDto> listAuthor(@Param("page") int page,
                                   @Param("pageSize") Integer pageSize);
}
