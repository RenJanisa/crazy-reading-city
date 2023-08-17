package com.banner.user.feign;

import com.banner.apis.user.IUserClient;
import com.banner.model.user.dtos.SimpleUserDto;
import com.banner.user.service.CrcUserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author rjj
 * @date 2023/7/31 - 17:19
 */

@RestController
public class UserClient implements IUserClient {

    @Resource
    private CrcUserService crcUserService;

    @Override
    @GetMapping("/simple-user/{id}")
    public SimpleUserDto get(@PathVariable("id") Long userId) {
        return crcUserService.getSimple(userId);
    }
}
