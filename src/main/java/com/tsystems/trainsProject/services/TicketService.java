package com.tsystems.trainsProject.services;

import com.tsystems.trainsProject.models.TicketEntity;

import java.util.List;

public interface TicketService {
    List<TicketEntity> findAllTickets();
    TicketEntity findByName(String name);
    void saveOrUpdate(TicketEntity station);
    TicketEntity findById(int id);
    String checkNumberOfTicket(TicketEntity ticket);
}
