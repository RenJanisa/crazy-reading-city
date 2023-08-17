package com.banner.search.controller;

import com.banner.model.common.dtos.ResponseResult;
import com.banner.model.search.dtos.GetBookSearchDto;
import com.banner.search.service.BookSearchService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author rjj
 * @date 2023/8/9 - 20:21
 */
@Api(tags = "书籍搜索服务")
@RestController
@RequestMapping("/crc_book_search")
public class BookSearchController {

    @Resource
    private BookSearchService bookSearchService;

    @ApiOperation("书名搜索")
    @PostMapping("/name")
    public ResponseResult searchByName(@RequestBody @Validated GetBookSearchDto getBookSearchDto){
        return bookSearchService.searchByName(getBookSearchDto);
    }


}
