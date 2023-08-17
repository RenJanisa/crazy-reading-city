package com.banner.common.filter;

import com.banner.common.utils.AppJwtUtil;
import io.jsonwebtoken.Claims;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpHeaders;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpCookie;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * @author rjj
 * @date 2023/7/25 - 21:16
 */
@Component
public class AuthorizeFilter implements Ordered, GlobalFilter {
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        //1.获取request和response对象
        ServerHttpRequest request = exchange.getRequest();
        ServerHttpResponse response = exchange.getResponse();

        //2.判断是否是登录或注册
        String path = request.getURI().getPath();
        if (path.contains("/login")
                || path.contains("/enroll")
                || path.contains("/send-code")) {
            //放行
            return chain.filter(exchange);
        }

        //3.获取token
        String token = request.getHeaders().getFirst(HttpHeaders.AUTHORIZATION);

        //4.判断token是否存在
        if (StringUtils.isBlank(token)) {
            response.setStatusCode(HttpStatus.UNAUTHORIZED);
            return response.setComplete();
        }

        //5.判断token是否有效
        try {
            Claims claimsBody = AppJwtUtil.getClaimsBody(token);
            //是否是过期
            int result = AppJwtUtil.checkTokenTime(claimsBody);
            if (result == 0) {
                response.setStatusCode(HttpStatus.UNAUTHORIZED);
                return response.setComplete();
            }
            //获得token解析后中的用户信息
            String userId = claimsBody.get("id").toString();
            String flag = claimsBody.get("flag").toString();
            if (result == 2){
                //token过期,刷新token
                response.addCookie(ResponseCookie
                        .from("token",AppJwtUtil.getToken(Long.valueOf(userId),Integer.parseInt(flag)))
                        .build());
            }
            //在header中添加新的信息
            ServerHttpRequest serverHttpRequest = request.mutate().headers(headers -> {
                headers.add("id", userId);
                headers.add("flag",flag);
            }).build();
            //重置header
            exchange.mutate().request(serverHttpRequest).build();
        } catch (Exception e) {
            e.printStackTrace();
            return response.setComplete();
        }

        //6.放行
        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
