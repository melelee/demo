package com.melelee.websocket;

// WebSocketConfiguration.java

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

/**
 * @author menglili
 */
@Configuration
// @EnableWebSocket // 无需添加该注解，因为我们并不是使用 Spring WebSocket
public class WebSocketConfiguration {

    @Bean
    public ServerEndpointExporter serverEndpointExporter() {
        return new ServerEndpointExporter();
    }

}
