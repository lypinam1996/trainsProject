package com.tsystems.trainsProject.services;

import com.tsystems.trainsProject.models.ScheduleEntity;
import com.tsystems.trainsProject.models.TicketEntity;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

public interface TicketService {
    List<TicketEntity> findAllTickets();
    TicketEntity findByName(String name);
    void saveOrUpdate(TicketEntity station);
    TicketEntity findById(int id);
    String checkNumberOfTicket(TicketEntity ticket);
    int findSeatWithMaxNumber(TicketEntity ticket);
    boolean checkTime(TicketEntity ticket);
    void delete1(TicketEntity ticketEntity);
}
