package com.melelee.websocket;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.socket.config.annotation.EnableWebSocket;

/**
 * @author menglili
 */
@SpringBootApplication
public class WebsocketSpringApplication {

    public static void main(String[] args) {
        SpringApplication.run(WebsocketSpringApplication.class, args);
    }

}
