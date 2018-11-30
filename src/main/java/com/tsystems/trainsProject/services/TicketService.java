package com.tsystems.trainsProject.services;

import com.tsystems.trainsProject.models.TicketEntity;

import java.util.Date;
import java.util.List;

public interface TicketService {
    List<TicketEntity> findAllTickets();
    void saveOrUpdate(TicketEntity station);
    TicketEntity findById(int id);
//    String checkNumberOfTicket(TicketEntity ticket);
    int findSeatWithMaxNumber(TicketEntity ticket);
    boolean checkTime(TicketEntity ticket);
    List<TicketEntity> findByDate(Date today);
    void deleteOldTickets();
}
