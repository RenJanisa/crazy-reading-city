package com.banner.book.controller;


import com.banner.book.service.CrcBookService;
import com.banner.model.book.dtos.GetBookMessageDto;
import com.banner.model.common.dtos.ResponseResult;
import com.banner.model.search.dtos.CrcBookSearchDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.checkerframework.checker.units.qual.C;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author rjj
 * @since 2023-07-27
 */
@RestController
@RequestMapping("/crc-book")
@Api(value = "书籍接口", tags = "书籍接口")
public class CrcBookController {

    @Resource
    private CrcBookService crcBookService;

    @ApiOperation(value = "查询书籍信息")
    @GetMapping("/book-info")
    public ResponseResult getBookInfo(String bookId){
        return crcBookService.getInfo(bookId);
    }

    @ApiOperation(value = "查询推荐书籍")
    @GetMapping("/recommend")
    public ResponseResult getRecommendBook(){
        List<CrcBookSearchDto> crcBookSearchDto = crcBookService.getRecommendBook();
        return ResponseResult.okResult(crcBookSearchDto);
    }


    @ApiOperation(value = "查询书籍摘录")
    @GetMapping("/excerpt")
    public ResponseResult getBookExcerpt(String bookId){
        return crcBookService.getBookExcerpt(bookId);
    }


    @ApiOperation(value = "查看聊天室消息")
    @PostMapping("/message")
    public ResponseResult getBookMsg(@RequestBody @Validated GetBookMessageDto getBookMessageDto){
        return crcBookService.getBookMessage(getBookMessageDto);
    }



}
