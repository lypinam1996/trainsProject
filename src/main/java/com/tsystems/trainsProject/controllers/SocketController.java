package com.tsystems.trainsProject.controllers;
import com.tsystems.trainsProject.DTO.HelloMessage;
import com.tsystems.trainsProject.models.PassangerEntity;
import com.tsystems.trainsProject.models.TicketEntity;
import com.tsystems.trainsProject.models.UserEntity;
import com.tsystems.trainsProject.services.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;

import java.awt.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

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

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @MessageMapping("/color")
    public void receiveColor(ColorMessage message){
        System.out.println("message.getColorString() = " + message.getColorString());
    }


    @Scheduled(fixedDelay = 1000)
    private void bgColor(){
        Random r = new Random();
        String m = String.valueOf(r.nextInt());
        String color = m.replace("0x", "#");
        simpMessagingTemplate.convertAndSend("/topic/color", new ColorMessage(color));
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
