package com.banner.apis.book;

import com.banner.model.book.dtos.BookSimpleDto;
import com.banner.model.search.dtos.CrcBookSearchDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @author rjj
 * @date 2023/8/10 - 11:21
 */
@FeignClient(value = "crc-book")
public interface IBookClient {


    @GetMapping("/get-book-list")
    List<CrcBookSearchDto> getBookSearchList();


    @GetMapping("/get-book-simple/{bookId}")
    BookSimpleDto getBookSimple(@PathVariable("bookId") Long bookId);
}
