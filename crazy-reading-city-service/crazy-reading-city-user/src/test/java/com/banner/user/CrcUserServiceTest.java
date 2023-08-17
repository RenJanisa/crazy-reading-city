package com.banner.user;

import com.banner.common.utils.AppJwtUtil;
import io.jsonwebtoken.Claims;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;

import javax.annotation.Resource;
import java.util.Map;

import static org.junit.Assert.*;

/**
 * @author rjj
 * @date 2023/7/25 - 20:55
 */
@SpringBootTest
public class CrcUserServiceTest {

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Test
    public void login() {
        Map<Object, Object> map = stringRedisTemplate.opsForHash().entries("");

    }
}