package com.banner.apis.user;

import com.banner.model.user.dtos.SimpleUserDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;

/**
 * @author rjj
 * @date 2023/7/31 - 17:13
 */
@FeignClient(value = "crc-user")
public interface IUserClient {

    @GetMapping("/simple-user/{id}")
    public SimpleUserDto get(@PathVariable("id")Long userId);

}
