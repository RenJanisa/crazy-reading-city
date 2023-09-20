package com.banner.book.controller;


import com.banner.book.service.CrcBookAuthorService;
import com.banner.book.service.CrcBookService;
import com.banner.model.common.dtos.PageDto;
import com.banner.model.common.dtos.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author rjj
 * @since 2023-07-27
 */
@Api(tags = "书籍作者接口")
@RestController
@RequestMapping("/crc-book-author")
public class CrcBookAuthorController {

    @Resource
    private CrcBookService crcBookService;
    @Resource
    private CrcBookAuthorService crcBookAuthorService;

    @ApiOperation("查询作者作品")
    @PostMapping("/book")
    public ResponseResult getAuthorBook(@RequestBody @Validated PageDto authorBook){
        return crcBookService.getAuthorBook(authorBook);
    }

    @ApiOperation("作者国籍列表")
    @GetMapping("/nationality")
    public ResponseResult getAuthorNationality(){
        return crcBookAuthorService.getAuthorNationality();
    }

    @ApiOperation("作者列表")
    @PostMapping("/list")
    public ResponseResult getAuthorList(@RequestBody @Validated PageDto authorCondition){
        return crcBookAuthorService.getBookAuthorList(authorCondition);
    }


}
