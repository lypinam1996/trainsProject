package com.tsystems.trainsProject.dao;

import com.tsystems.trainsProject.models.TicketEntity;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

public interface TicketDAO {
    List<TicketEntity> findAllTickets();
    void saveOrUpdate(TicketEntity station);
    TicketEntity findById(int id);
    List<TicketEntity> findByDate(Date today) throws ParseException;
}
