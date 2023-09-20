package com.banner.book.feign;

import com.banner.apis.book.IBookClient;
import com.banner.book.service.CrcBookAuthorService;
import com.banner.book.service.CrcBookService;
import com.banner.model.book.dtos.BookSimpleDto;
import com.banner.model.search.dtos.CrcBookAuthorSearchDto;
import com.banner.model.search.dtos.CrcBookSearchDto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author rjj
 * @date 2023/8/10 - 11:28
 */
@RestController
public class BookClient implements IBookClient {

    @Resource
    private CrcBookService crcBookService;


    @Override
    @GetMapping("/get-book-list")
    public List<CrcBookSearchDto> getBookSearchList() {
        return crcBookService.getBookSearchList();
    }

    @Override
    @GetMapping("/get-book-simple/{bookId}")
    public BookSimpleDto getBookSimple(@PathVariable("bookId")Long bookId) {
        return crcBookService.getBookSimple(bookId);
    }

    @Resource
    private CrcBookAuthorService crcBookAuthorService;

    @Override
    @GetMapping("/get-book-author-list")
    public List<CrcBookAuthorSearchDto> getBookAuthorSearchList() {
        return crcBookAuthorService.getBookAuthorSearchList();
    }
}
