package com.tsystems.trainsProject.controllers;
import com.tsystems.trainsProject.models.HelloMessage;
import com.tsystems.trainsProject.models.TicketEntity;
import com.tsystems.trainsProject.services.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.util.HtmlUtils;

import java.util.Date;
import java.util.List;

@Controller
public class SocketController {

    @Autowired
    TicketService ticketService;

    @MessageMapping("/hello")
    @SendTo("/topic/greetings")
    public String greeting(HelloMessage message) throws Exception {
        Date today = new Date();
        List<TicketEntity> tickets = ticketService.findByDate(today);
        if(!tickets.isEmpty()) {
            return ("You have some tickets for today");
        }
        else {
            return "";
        }
    }

}
