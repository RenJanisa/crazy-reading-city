package com.banner.admin.controller;


import com.banner.admin.service.CrcBookAuthorService;
import com.banner.admin.service.CrcBookService;
import com.banner.admin.service.CrcBookTypeService;
import com.banner.model.book.pojos.CrcBook;
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
    public ResponseResult addBook(@RequestBody @Validated CrcBook crcBook) {
        return crcBookService.addBook(crcBook);
    }

    @ApiOperation("添加作者")
    @PostMapping("/add-author")
    public ResponseResult addAuthor(@RequestBody @Validated CrcBookAuthor crcBookAuthor){
        return crcBookAuthorService.addAuthor(crcBookAuthor);
    }





}
