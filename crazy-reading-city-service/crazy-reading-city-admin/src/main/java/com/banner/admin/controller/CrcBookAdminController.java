package com.banner.admin.controller;


import com.banner.admin.service.CrcBookAuthorService;
import com.banner.admin.service.CrcBookService;
import com.banner.admin.service.CrcBookTypeService;
import com.banner.model.book.dtos.AUBookDto;
import com.banner.model.common.dtos.PageDto;
import com.banner.model.book.pojos.CrcBookAuthor;
import com.banner.model.book.pojos.CrcBookType;
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
@RestController
@RequestMapping("/crc-book-admin")
@Api(tags = "后台书籍接口")
public class CrcBookAdminController {

    @Resource
    private CrcBookService crcBookService;

    @Resource
    private CrcBookTypeService crcBookTypeService;

    @Resource
    private CrcBookAuthorService crcBookAuthorService;

    @ApiOperation("添加类型")
    @PostMapping("/type-add")
    public ResponseResult addType(@RequestBody @Validated CrcBookType crcBookType) {
        return crcBookTypeService.addType(crcBookType);
    }

    @ApiOperation("添加书籍")
    @PostMapping("/book-add")
    public ResponseResult addBook(@RequestBody @Validated AUBookDto crcBook) {
        return crcBookService.addBook(crcBook);
    }

    @ApiOperation("修改书籍信息")
    @PutMapping("/book-update")
    public ResponseResult updateBook(@RequestBody @Validated AUBookDto auBookDto){
        return crcBookService.updateBook(auBookDto);
    }

    @ApiOperation("删除书籍")
    @DeleteMapping("/book-delete")
    public ResponseResult delBook(String bookIds){
        return crcBookService.delBook(bookIds);
    }

    @ApiOperation("查看书籍列表")
    @PostMapping("/book-list")
    public ResponseResult listBook(@RequestBody @Validated PageDto pageDto){
        return crcBookService.listBook(pageDto);
    }

    @ApiOperation("添加作者")
    @PostMapping("/author-add")
    public ResponseResult addAuthor(@RequestBody @Validated CrcBookAuthor crcBookAuthor){
        return crcBookAuthorService.addAuthor(crcBookAuthor);
    }

    @ApiOperation("更新作者")
    @PutMapping("/author-update")
    public ResponseResult updateAuthor(@RequestBody @Validated CrcBookAuthor crcBookAuthor){
        return crcBookAuthorService.updateAuthor(crcBookAuthor);
    }

    @ApiOperation("删除作者")
    @DeleteMapping("/author-delete")
    public ResponseResult delAuthor(String authorIds){
        return crcBookAuthorService.delAuthor(authorIds);
    }

    @ApiOperation("查看作者列表")
    @PostMapping("/author-list")
    public ResponseResult listAuthor(@RequestBody @Validated PageDto authorPage){
        return crcBookAuthorService.listAuthor(authorPage);
    }






}
