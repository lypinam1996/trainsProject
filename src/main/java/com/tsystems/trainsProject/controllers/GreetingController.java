package com.tsystems.trainsProject.controllers;

import com.tsystems.trainsProject.models.Greeting;
import com.tsystems.trainsProject.models.HelloMessage;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.util.HtmlUtils;

@Controller
public class GreetingController {

    @MessageMapping("/hello")
    @SendTo("/topic/greetings")
    public String greeting(HelloMessage message) throws Exception {
        Thread.sleep(1000); // simulated delay
        return ("Hello, " + HtmlUtils.htmlEscape(message.getName()) + "!");
    }

}
