package org.openbox.dashboard.websocket;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan("org.openbox.dashboard")
@SpringBootApplication
public class WebSocketApplication {

    public static void main(String[] args) {
        SpringApplication.run(WebSocketApplication.class, args);
    }
    public static void start() {
        main(new String[]{});
    }
}
