package com.melelee.websocket;

// WebSocketConfiguration.java

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

/**
 * @author menglili
 */
@Configuration
@EnableWebSocket // 开启 Spring WebSocket
public class WebSocketConfiguration implements WebSocketConfigurer {

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(this.webSocketHandler(), "/") // 配置处理器
                .addInterceptors(new DemoWebSocketShakeInterceptor()) // 配置拦截器
                .setAllowedOrigins("*"); // 解决跨域问题
    }

    @Bean
    public DemoWebSocketHandler webSocketHandler() {
        return new DemoWebSocketHandler();
    }

    @Bean
    public DemoWebSocketShakeInterceptor webSocketShakeInterceptor() {
        return new DemoWebSocketShakeInterceptor();
    }

}
