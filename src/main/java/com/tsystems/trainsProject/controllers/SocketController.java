package com.tsystems.trainsProject.controllers;

import com.tsystems.trainsProject.DTO.Converter;
import com.tsystems.trainsProject.DTO.HelloMessage;
import com.tsystems.trainsProject.DTO.ScheduleDTO;
import com.tsystems.trainsProject.Events.CustomSpringEvent;
import com.tsystems.trainsProject.models.PassangerEntity;
import com.tsystems.trainsProject.models.ScheduleEntity;
import com.tsystems.trainsProject.models.TicketEntity;
import com.tsystems.trainsProject.models.UserEntity;
import com.tsystems.trainsProject.services.ScheduleService;
import com.tsystems.trainsProject.services.TicketService;
import com.tsystems.trainsProject.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
public class SocketController implements ApplicationListener<CustomSpringEvent> {
    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @Autowired
    TicketService ticketService;

    @Autowired
    ScheduleService scheduleService;

    @Scheduled(fixedDelay = 10000)
    private void todayTickets() {
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
        simpMessagingTemplate.convertAndSend("/topic/greetings", result);
    }
    
    @Override
    public void onApplicationEvent(CustomSpringEvent customSpringEvent) {
        List<ScheduleDTO> schedule = new ArrayList<>();
        try {
            schedule.addAll(helper());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        finally {
            simpMessagingTemplate.convertAndSend("/socket/schedule", schedule);
        }
    }

    private List<ScheduleDTO> helper() throws ParseException {
        List<ScheduleDTO> scheduleDTO = new ArrayList<ScheduleDTO>();
        List<ScheduleEntity> scheduleEntities = scheduleService.findAllSchedulesAfterTime();
        Converter converter = new Converter();
        for (int i = 0; i < scheduleEntities.size(); i++) {
            scheduleDTO.add(converter.convertSchedule(scheduleEntities.get(i)));
        }
        return scheduleDTO;
    }
}
