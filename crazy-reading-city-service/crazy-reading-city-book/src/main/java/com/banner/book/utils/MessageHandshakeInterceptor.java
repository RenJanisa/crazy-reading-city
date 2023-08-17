package com.banner.book.utils;

import com.banner.common.utils.ThreadLocalUtil;
import com.sun.prism.impl.BaseContext;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import java.util.Map;


/**
 * @author rjj
 * @date 2022/10/13 - 20:19
 */
@Slf4j
public class MessageHandshakeInterceptor implements HandshakeInterceptor {


    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Map<String, Object> attributes) throws Exception {

        String path = request.getURI().getPath();
        String[] flag = StringUtils.split(path, "/");
        if (flag.length != 3){
            response.setStatusCode(HttpStatus.BAD_REQUEST);
            return false;
        }
        return true;
    }

    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Exception exception) {
        log.info("握手后....");
    }
}
