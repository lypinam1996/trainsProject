package com.tsystems.trainsProject.dao;

import com.tsystems.trainsProject.models.TicketEntity;

import java.util.List;

public interface TicketDAO {
    List<TicketEntity> findAllTickets();
    TicketEntity findByName(String name);
    void saveOrUpdate(TicketEntity station);
    TicketEntity findById(int id);
}
