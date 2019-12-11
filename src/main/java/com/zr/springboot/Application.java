package com.zr.springboot;

import com.zr.springboot.commom.Common;
import com.zr.springboot.socket.WebSocket;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);

        Common.startConsumer(WebSocket.consumer, WebSocket.webSocketSet, Common.TOPIC, Common.PARTITION);

    }

}
