package com.ishmamruhan.imageservice.Controller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class SocketController {

    /**
     * This controller and mapping will use if we want to send any data from frontend.
     */

    @MessageMapping("/message")
    @SendTo("/topic/-topicName-")
    public void executeTask(){

    }
}
