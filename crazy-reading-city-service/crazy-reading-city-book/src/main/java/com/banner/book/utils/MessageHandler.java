package com.banner.book.utils;

import cn.hutool.json.JSONUtil;
import com.banner.apis.user.IUserClient;
import com.banner.book.mapper.BookMessageMapper;
import com.banner.common.utils.ThreadLocalUtil;

import com.banner.model.book.pojos.Message;
import com.banner.model.user.dtos.SimpleUserDto;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.prism.impl.BaseContext;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static com.banner.common.constants.RedisConstants.BOOK_TALK_KEY;

/**
 * @author rjj
 * @date 2022/10/13 - 19:36
 * 私信处理器
 */
@Slf4j
public class MessageHandler extends TextWebSocketHandler {

    @Resource
    private BookMessageMapper bookMessageMapper;

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    private static final Map<String, WebSocketSession> SESSIONS = new HashMap<>();


    //用户和服务端建立连接后
    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        assert session.getUri() != null;
        String[] flag = session.getUri().getPath().split("/");
        String bookId = flag[2];
        String userId = flag[3];
        log.info("用户和服务端建立连接后......{}", bookId);
        String sessionId = String.valueOf(IdWorker.getId());
        //将用户session放入redis,后续会使用相应session通信
        assert session.getUri() != null;
        stringRedisTemplate.opsForHash().put(BOOK_TALK_KEY + bookId
                , userId
                , sessionId);
        SESSIONS.put(sessionId, session);
    }

    //用户关闭连接后
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        assert session.getUri() != null;
        String[] flag = session.getUri().getPath().split("/");
        String bookId = flag[2];
        String userId = flag[3];
        log.info("用户和服务端连接关闭......{}", bookId);
        String sessionId = stringRedisTemplate.opsForHash().get(BOOK_TALK_KEY + bookId, userId).toString();
        SESSIONS.remove(sessionId);
        stringRedisTemplate.opsForHash().delete(BOOK_TALK_KEY + bookId, userId);
    }


    @Resource
    private IUserClient userClient;

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage textMessage) throws IOException {

        //获取用户id
        assert session.getUri() != null;
        String[] flag = session.getUri().getPath().split("/");
        String bookId = flag[2];
        String userId = flag[3];

        Message message = JSONUtil.toBean(textMessage.getPayload(), Message.class);
        message.setUserId(userId);
        message.setBookId(bookId);
        SimpleUserDto simpleUserDto = userClient.get(Long.valueOf(userId));
        message.setUserName(simpleUserDto.getUserName());
        message.setAvatar(simpleUserDto.getAvatar());

        Message messageAfter = bookMessageMapper.saveToMongo(message);

        String key = BOOK_TALK_KEY + bookId;
        //判断书籍聊天室是否有用户在线
        Long size = stringRedisTemplate.opsForHash().size(key);
        if (size > 0) {
            String messageJSON = JSONUtil.toJsonStr(messageAfter);
            Map<Object, Object> userOnline = stringRedisTemplate.opsForHash().entries(key);
            for (Object onlineId : userOnline.keySet()) {
                WebSocketSession webSocketSession = SESSIONS.get(userOnline.get(onlineId).toString());
                if (webSocketSession.isOpen()) {
                    webSocketSession.sendMessage(new TextMessage(messageJSON));
                }
            }

        }

    }
}
