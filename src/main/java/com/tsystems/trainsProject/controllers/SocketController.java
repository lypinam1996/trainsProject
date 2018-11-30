package com.tsystems.trainsProject.controllers;

import com.tsystems.trainsProject.events.EditingEvent;
import com.tsystems.trainsProject.dto.Converter;
import com.tsystems.trainsProject.dto.HelloMessage;
import com.tsystems.trainsProject.dto.ScheduleDTO;
import com.tsystems.trainsProject.dto.TicketDto;
import com.tsystems.trainsProject.models.ScheduleEntity;
import com.tsystems.trainsProject.models.TicketEntity;
import com.tsystems.trainsProject.services.ScheduleService;
import com.tsystems.trainsProject.services.TicketService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
public class SocketController implements ApplicationListener<EditingEvent>  {

    private static final Logger logger = Logger.getLogger(SocketController.class);
    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @Autowired
    TicketService ticketService;

    @Autowired
    ScheduleService scheduleService;

    @Scheduled(fixedDelay = 5000)
    private void todayTickets() {
        logger.info("SocketController: start to send message with today's ticket");
        List<HelloMessage> result = new ArrayList<HelloMessage>();
        Date today = new Date();
        List<TicketEntity> tickets = ticketService.findByDate(today);
        if (!tickets.isEmpty()) {
            for (int i = 0; i < tickets.size(); i++) {
                HelloMessage message = new HelloMessage();
                message.setName(tickets.get(i).getPassanger().getUser().getLogin());
                result.add(message);
            }
        }
        simpMessagingTemplate.convertAndSend("/topic/today", result);
        logger.info("SocketController: message has been sent");
    }

    @Scheduled(fixedDelay = 10000)
    private void deleteTickets() {
        logger.info("SocketController: start to send message with today's ticket");
        ticketService.deleteOldTickets();
        List<TicketEntity> tickets = ticketService.findAllTickets();
        List<TicketDto> ticketDtos = new ArrayList<>();
        Converter converter = new Converter();
        for (int i = 0; i < tickets.size(); i++) {
            ticketDtos.add(converter.convertTicket(tickets.get(i)));
        }
        simpMessagingTemplate.convertAndSend("/topic/ticketDelete", ticketDtos);
        logger.info("SocketController: message has been sent");
    }

    @Override
    public void onApplicationEvent(EditingEvent customSpringEvent) {
        logger.info("SocketController: the event has been gotten");
        try {
            int id = Integer.parseInt(customSpringEvent.getMessage());
            int type = customSpringEvent.getType();
            if(type==0) {
                ScheduleEntity schedule = scheduleService.findById(id);
                Converter converter = new Converter();
                ScheduleDTO scheduleDto = converter.convertSchedule(schedule);
                logger.info("SocketController: start to send message with schedule");
                simpMessagingTemplate.convertAndSend("/topic/schedule", scheduleDto);
                logger.info("SocketController: message has been sent");
            }
            else {
                simpMessagingTemplate.convertAndSend("/topic/schedule", id);
            }
        }
        catch (Exception e){
            logger.info(e.getMessage());
            e.printStackTrace();
        }
    }
}
