package com.tsystems.trainsProject.controllers;
import com.tsystems.trainsProject.DTO.HelloMessage;
import com.tsystems.trainsProject.models.PassangerEntity;
import com.tsystems.trainsProject.models.TicketEntity;
import com.tsystems.trainsProject.models.UserEntity;
import com.tsystems.trainsProject.services.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;

import java.util.ArrayList;
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

//    @MessageMapping("/hello")
//    @SendTo("/topic/greetings")
//    public String greeting(HelloMessage message) throws Exception {
//        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//        UserEntity user = userService.findByLogin(auth.getName());
//        List<PassangerEntity> passangers = user.getPassanger();
//        List<TicketEntity> tickets = new ArrayList<>();
//        for (int i = 0; i < passangers.size(); i++) {
//            tickets.addAll(passangers.get(i).getTickets());
//        }
//        Date today = new Date();
//        List<TicketEntity> result = ticketService.findByDate(today,tickets);
//        if(!result.isEmpty()) {
//            return ("You have some tickets for today");
//        }
//        else {
//            return "";
//        }
//    }

}
