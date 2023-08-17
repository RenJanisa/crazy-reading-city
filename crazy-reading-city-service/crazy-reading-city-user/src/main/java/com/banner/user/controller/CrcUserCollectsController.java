package com.banner.user.controller;


import com.banner.model.common.dtos.ResponseResult;
import com.banner.model.user.dtos.CollectAddDto;
import com.banner.user.mapper.CrcUserCollectsMapper;
import com.banner.user.service.CrcUserCollectsService;
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
@RestController
@Api(value = "用户收藏夹接口",tags = "用户收藏夹接口")
@RequestMapping("/crc-user-collects")
public class CrcUserCollectsController {

    @Resource
    private CrcUserCollectsService crcUserCollectsService;

    @ApiOperation("添加收藏夹")
    @PostMapping("/add")
    public ResponseResult add(@RequestBody @Validated CollectAddDto collectAddDto){
        return crcUserCollectsService.add(collectAddDto);
    }

    @ApiOperation("查看收藏夹")
    @GetMapping("/get")
    public ResponseResult get(Long userId){
        return crcUserCollectsService.get(userId);
    }


}
