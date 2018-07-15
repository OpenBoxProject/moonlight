package org.openbox.dashboard.websocket;

import com.google.gson.Gson;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class DashboardController {
    @MessageMapping("/hello")
    @SendTo("/topic/greetings")
    public Greeting greeting(@Payload String messageStr) throws Exception {
        HelloMessage message = new Gson().fromJson(messageStr, HelloMessage.class);

        System.out.println(message.getName());
        Thread.sleep(1000); // simulated delay
        return new Greeting("Hello, " + message.getName() + "!");
    }

    public static void start() {

    }

    public class Greeting {

        private String content;

        public Greeting() {
        }

        public Greeting(String content) {
            this.content = content;
        }

        public String getContent() {
            return content;
        }

    }

    public class HelloMessage {

        private String name;

        public HelloMessage() {
        }

        public HelloMessage(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
