package com.tsystems.trainsProject.services;

import com.tsystems.trainsProject.models.PassangerEntity;
import com.tsystems.trainsProject.models.TicketEntity;
import org.springframework.validation.BindingResult;

import java.util.Date;
import java.util.List;

public interface TicketService {
    List<TicketEntity> findAllTickets();
    void saveOrUpdate(TicketEntity station);
    TicketEntity findById(int id);
    int findSeatWithMaxNumber(TicketEntity ticket);
    List<TicketEntity> findByDate(Date today);
    void deleteOldTickets();
    BindingResult validation(BindingResult bindingResult, TicketEntity ticket);
}
