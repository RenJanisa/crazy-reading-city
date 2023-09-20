package com.banner.book.service;

import com.banner.model.common.dtos.PageDto;
import com.banner.model.book.dtos.BookSimpleDto;
import com.banner.model.book.dtos.GetBookMessageDto;
import com.banner.model.book.pojos.CrcBook;
import com.banner.model.common.dtos.ResponseResult;
import com.banner.model.search.dtos.CrcBookSearchDto;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author rjj
 * @since 2023-07-27
 */
public interface CrcBookService extends IService<CrcBook> {

    ResponseResult getInfo(String bookId);

    ResponseResult getBookExcerpt(String bookId);

    ResponseResult getBookMessage(GetBookMessageDto getBookMessageDto);

    List<CrcBookSearchDto> getBookSearchList();

    BookSimpleDto getBookSimple(Long bookId);

    List<CrcBookSearchDto> getRecommendBook();

    ResponseResult getAuthorBook(PageDto authorBook);
}
