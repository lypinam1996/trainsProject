package com.tsystems.trainsProject.services.impl;

import com.tsystems.trainsProject.dao.impl.TicketDAOImpl;
import com.tsystems.trainsProject.models.TicketEntity;
import com.tsystems.trainsProject.services.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("TicketService")
@Transactional
public class TicketServiceImpl implements TicketService {

    @Autowired
    TicketDAOImpl ticketDAO;

    @Override
    public List<TicketEntity> findAllTickets() {
        return ticketDAO.findAllTickets();
    }

    @Override
    public TicketEntity findByName(String name) {
        return null;
    }

    @Override
    public void saveOrUpdate(TicketEntity ticket) {
        ticketDAO.saveOrUpdate(ticket);
    }

    @Override
    public TicketEntity findById(int id) {
        return ticketDAO.findById(id);
    }
}
