package com.banner.book.utils;

import cn.hutool.core.util.StrUtil;
import com.banner.common.utils.AppJwtUtil;
import com.banner.common.utils.ThreadLocalUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpHeaders;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

/**
 * @author rjj
 * @date 2023/7/26 - 9:39
 */
@Slf4j
public class BookTokenInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //得到header中的信息
        String id = request.getHeader("id");
        if(StrUtil.isNotBlank(id)){
            //把用户id存入threadloacl中
            ThreadLocalUtil.setId(Long.valueOf(id));
            log.info("TokenFilter设置用户信息到threadlocal中...");
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        log.info("清理threadlocal...");
        ThreadLocalUtil.clear();
    }
}