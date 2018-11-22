package com.tsystems.trainsProject.controllers;
import com.tsystems.trainsProject.Events.CustomSpringEvent;
import com.tsystems.trainsProject.models.TicketEntity;
import com.tsystems.trainsProject.services.TicketService;
import com.tsystems.trainsProject.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;

import java.util.Date;
import java.util.List;

@Controller
//public class SocketController implements ApplicationListener<CustomSpringEvent> {
public class SocketController{
    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @Autowired
    TicketService ticketService;

    @Autowired
    UserService userService;

    @Scheduled(fixedDelay = 10000)
    private void bgColor(){
        String result = "";
        Date today = new Date();
        List<TicketEntity> tickets = ticketService.findByDate(today);
        if(!tickets.isEmpty()) {
            result="You have some tickets for today";
        }
        simpMessagingTemplate.convertAndSend("/topic/greetings",result );
    }

//    @Override
//    public void onApplicationEvent(CustomSpringEvent customSpringEvent) {
//        simpMessagingTemplate.convertAndSend("/topic/greetings",customSpringEvent.getMessage() );
//    }
}
