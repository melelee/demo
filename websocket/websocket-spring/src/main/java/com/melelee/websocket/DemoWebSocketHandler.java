package com.melelee.websocket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.Map;

public class DemoWebSocketHandler extends TextWebSocketHandler {

    private Logger logger = LoggerFactory.getLogger(getClass());


    @Autowired
    private ApplicationContext applicationContext;

    @Override // 对应 open 事件
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        logger.info("[afterConnectionEstablished][session({}) 接入]", session);
        Map<String, Object> attributes = session.getAttributes();
        System.out.println(attributes);
    }

    @Override // 对应 message 事件
    public void handleTextMessage(WebSocketSession session, TextMessage textMessage) throws Exception {
        logger.info("[handleMessage][session({}) 接收到一条消息({})]", session, textMessage); // 生产环境下，请设置成 debug 级别
    }

    @Override // 对应 close 事件
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        logger.info("[afterConnectionClosed][session({}) 连接关闭。关闭原因是({})}]", session, status);
    }

    @Override // 对应 error 事件
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        logger.info("[handleTransportError][session({}) 发生异常]", session, exception);
    }

}
