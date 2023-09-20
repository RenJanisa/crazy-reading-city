package com.banner.apis.user;

import com.banner.model.admin.dtos.AdminReportDto;
import com.banner.model.admin.dtos.AdminUserListDto;
import com.banner.model.common.dtos.PageDto;
import com.banner.model.user.dtos.SimpleUserDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @author rjj
 * @date 2023/7/31 - 17:13
 */
@FeignClient(value = "crc-user")
public interface IUserClient {

    @GetMapping("/simple-user/{id}")
    public SimpleUserDto get(@PathVariable("id")Long userId);

    @GetMapping("/user-report")
    AdminReportDto getUserReport();

    @PostMapping("/user-list")
    Map getUserList(@RequestBody PageDto pageDto);
}
