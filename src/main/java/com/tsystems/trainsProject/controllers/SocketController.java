package com.tsystems.trainsProject.controllers;

import com.tsystems.trainsProject.dto.Converter;
import com.tsystems.trainsProject.dto.HelloMessage;
import com.tsystems.trainsProject.dto.ScheduleDTO;
import com.tsystems.trainsProject.Events.CustomSpringEvent;
import com.tsystems.trainsProject.dto.TicketDto;
import com.tsystems.trainsProject.models.ScheduleEntity;
import com.tsystems.trainsProject.models.TicketEntity;
import com.tsystems.trainsProject.services.ScheduleService;
import com.tsystems.trainsProject.services.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
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

    @Scheduled(fixedDelay = 5000)
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
        simpMessagingTemplate.convertAndSend("/topic/ticketDelete", result);
    }
//нет смысла посылать id, т.к. удаляется не 1 билет, а много
    @Scheduled(fixedDelay = 10000)
    private void deleteTickets() {
        ticketService.deleteOldTickets();
        List<TicketEntity> tickets = ticketService.findAllTickets();
        List<TicketDto> ticketDtos = new ArrayList<>();
        Converter converter = new Converter();
        for (int i = 0; i < tickets.size(); i++) {
            ticketDtos.add(converter.convertTicket(tickets.get(i)));
        }
        simpMessagingTemplate.convertAndSend("/topic/delete", ticketDtos);
    }

    //по ивенту сделать удаление
    @Override
    public void onApplicationEvent(CustomSpringEvent customSpringEvent) {
        try {
            int id = Integer.parseInt(customSpringEvent.getMessage());
            ScheduleEntity schedule = scheduleService.findById(id);
            Converter converter = new Converter();
            ScheduleDTO scheduleDto = converter.convertSchedule(schedule);
            simpMessagingTemplate.convertAndSend("/topic/schedule", scheduleDto);
        }
        catch (Exception e){
            e.printStackTrace();
        }

    }
}
