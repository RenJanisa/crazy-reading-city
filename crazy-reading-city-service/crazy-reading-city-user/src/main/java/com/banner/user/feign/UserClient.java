package com.banner.user.feign;

import com.banner.apis.user.IUserClient;
import com.banner.model.admin.dtos.AdminReportDto;
import com.banner.model.admin.dtos.AdminUserListDto;
import com.banner.model.common.dtos.PageDto;
import com.banner.model.user.dtos.SimpleUserDto;
import com.banner.user.service.CrcUserCommentService;
import com.banner.user.service.CrcUserPlanService;
import com.banner.user.service.CrcUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @author rjj
 * @date 2023/7/31 - 17:19
 */

@Slf4j
@RestController
public class UserClient implements IUserClient {

    @Resource
    private CrcUserService crcUserService;

    @Resource
    private CrcUserPlanService crcUserPlanService;

    @Resource
    private CrcUserCommentService crcUserCommentService;

    @Override
    @GetMapping("/simple-user/{id}")
    public SimpleUserDto get(@PathVariable("id") Long userId) {
        return crcUserService.getSimple(userId);
    }

    @Override
    @GetMapping("/user-report")
    public AdminReportDto getUserReport() {

        return new AdminReportDto(null,null,null,
                crcUserService.count(),
                crcUserCommentService.count(),
                crcUserPlanService.count()
                );
    }

    @Override
    @PostMapping("/user-list")
    public Map getUserList(@RequestBody PageDto pageDto) {
        return crcUserService.getUserList(pageDto);
    }
}
