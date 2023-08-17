package com.banner.book.controller;


import com.banner.book.service.CrcBookTypeService;
import com.banner.model.book.dtos.TypeBookDto;
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
 * @since 2023-07-31
 */
@Api(tags = "书籍类型接口")
@RestController
@RequestMapping("/crc-book-type")
public class CrcBookTypeController {

    @Resource
    private CrcBookTypeService crcBookTypeService;

    @ApiOperation("类型查询")
    @GetMapping("/get")
    public ResponseResult getType(String typeId) {
        return crcBookTypeService.getType(typeId);
    }

    @ApiOperation("查询类型所含书籍")
    @PostMapping("/get/book")
    public ResponseResult getTypeBook(@RequestBody @Validated TypeBookDto typeBookDto){

        return crcBookTypeService.getTypeBook(typeBookDto);
    }



}