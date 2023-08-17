package com.banner.book.controller;


import com.banner.book.service.CrcBookCollectService;
import com.banner.model.book.pojos.CrcBookCollect;
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
@Api(value = "crc-book-collect",tags = "收藏夹内容接口")
@RestController
@RequestMapping("/crc-book-collect")
public class CrcBookCollectController {

    @Resource
    private CrcBookCollectService crcBookCollectService;

    @ApiOperation("查看收藏夹内容")
    @GetMapping("/get")
    public ResponseResult getIn(Long collectId){
        return crcBookCollectService.getIn(collectId);
    }

    @ApiOperation("添加到收藏夹")
    @PostMapping("/add")
    public ResponseResult add(@RequestBody @Validated CrcBookCollect crcBookCollect){
        return crcBookCollectService.add(crcBookCollect);
    }

}
