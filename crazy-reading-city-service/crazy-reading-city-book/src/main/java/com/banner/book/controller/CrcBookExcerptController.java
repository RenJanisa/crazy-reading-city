package com.banner.book.controller;


import com.banner.book.service.CrcBookExcerptService;
import com.banner.book.service.CrcBookScanService;
import com.banner.model.book.dtos.CrcBookExcerptDto;
import com.banner.model.book.pojos.CrcBookExcerpt;
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
 * @since 2023-07-28
 */
@RestController
@RequestMapping("/crc-book-excerpt")
@Api(value = "摘录接口",tags = "摘录接口")
public class CrcBookExcerptController {

    @Resource
    private CrcBookExcerptService crcBookExcerptService;

    @ApiOperation(value = "添加摘录")
    @PostMapping("/add")
    public ResponseResult addExcerpt(@RequestBody @Validated CrcBookExcerptDto crcBookExcerptDto) {
        return crcBookExcerptService.addExcerpt(crcBookExcerptDto);
    }

    @ApiOperation(value = "查看摘录")
    @GetMapping("/get")
    public ResponseResult getExcerpt(Long excerptId){
        return crcBookExcerptService.getExcerpt(excerptId);
    }

    @ApiOperation("点赞/取消")
    @PostMapping("/like")
    public ResponseResult like(String excerptId,String userId){
        return crcBookExcerptService.like(excerptId,userId);
    }



}
