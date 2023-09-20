package com.banner.search.controller;

import com.banner.model.common.dtos.PageDto;
import com.banner.model.common.dtos.ResponseResult;
import com.banner.search.service.SearchService;
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
@RequestMapping("/crc_search")
public class SearchController {

    @Resource
    private SearchService searchService;

    @ApiOperation("书名搜索")
    @PostMapping("/book-name")
    public ResponseResult searchBookByName(@RequestBody @Validated PageDto getBookSearchDto){
        return searchService.searchBookByName(getBookSearchDto);
    }

    @ApiOperation("查看搜索记录")
    @GetMapping("/search-history")
    public ResponseResult getSearchHistory(String userId){
        return searchService.getSearchHistory(userId);
    }

    @ApiOperation("清空搜索记录")
    @DeleteMapping("/del-history")
    public ResponseResult delSearchHistory(String userId){
        return searchService.delSearchHistory(userId);
    }

    @ApiOperation("作者名搜索")
    @GetMapping("/book-author-name")
    public ResponseResult searchBookAuthorByName(String authorName){
        return searchService.searchBookAuthorByName(authorName);
    }


}
