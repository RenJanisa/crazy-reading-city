package com.banner.book.config;


import com.banner.book.utils.MessageHandler;
import com.banner.book.utils.MessageHandshakeInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

import javax.annotation.Resource;

/**
 * @author rjj
 * @date 2022/10/13 - 20:08
 */
@Configuration
@EnableWebSocket
@Import(MessageHandler.class)
public class WebSocketConfig implements WebSocketConfigurer {

    @Resource
    private MessageHandler messageHandler;

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(this.messageHandler,"/ws/{bookId}/{userId}")
                //允许跨域
                .setAllowedOrigins("*")
                .addInterceptors(new MessageHandshakeInterceptor());
    }
}
