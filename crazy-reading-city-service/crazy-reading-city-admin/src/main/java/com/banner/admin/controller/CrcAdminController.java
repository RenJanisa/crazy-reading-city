package com.banner.admin.controller;

import com.banner.admin.service.CrcBookAuthorService;
import com.banner.admin.service.CrcBookExcerptService;
import com.banner.admin.service.CrcBookService;
import com.banner.apis.user.IUserClient;
import com.banner.model.admin.dtos.AdminReportDto;
import com.banner.model.common.dtos.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

/**
 * @author rjj
 * @date 2023/8/24 - 10:09
 */
@Api(tags = "后台系统接口")
@RestController
@RequestMapping("/crc-admin")
@Slf4j
public class CrcAdminController {

    @Resource
    private CrcBookService crcBookService;
    @Resource
    private CrcBookAuthorService crcBookAuthorService;
    @Resource
    private CrcBookExcerptService crcBookExcerptService;
    @Resource
    private ExecutorService executorService;
    @Resource
    private IUserClient userClient;

    @ApiOperation("查询统计信息")
    @GetMapping("/report")
    public ResponseResult getReport() {

//        线程1查询书籍
        Future<AdminReportDto> r1 = executorService.submit(() -> {
            AdminReportDto adminReportDto = new AdminReportDto();
            adminReportDto.setBookCount(crcBookService.count());
            adminReportDto.setBookExcerptCount(crcBookExcerptService.count());
            adminReportDto.setBookAuthorCount(crcBookAuthorService.count());
            return adminReportDto;
        });

//        线程2查询用户
        Future<AdminReportDto> r2 = executorService.submit(() -> userClient.getUserReport());

        AdminReportDto adminReportDto;
        try {
            adminReportDto = r1.get();
            adminReportDto.setUserCount(r2.get().getUserCount());
            adminReportDto.setUserCommentCount(r2.get().getUserCommentCount());
            adminReportDto.setUserPlanCount(r2.get().getUserPlanCount());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return ResponseResult.okResult(adminReportDto);
    }




}
