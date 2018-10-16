package com.tsystems.trainsProject.services.impl;

import com.tsystems.trainsProject.dao.impl.TicketDAOImpl;
import com.tsystems.trainsProject.models.BranchLineEntity;
import com.tsystems.trainsProject.models.PassangerEntity;
import com.tsystems.trainsProject.models.ScheduleEntity;
import com.tsystems.trainsProject.models.TicketEntity;
import com.tsystems.trainsProject.services.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
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

    public String checkNumberOfTicket(TicketEntity ticket){
        String error="";
        List<TicketEntity> ticketEntities = new ArrayList<>();
        ScheduleEntity schedule = ticket.getSchedule();
        List<TicketEntity> tickets = schedule.getTicket();
        for(int i=0;i<tickets.size();i++){
            if(tickets.get(i).getDepartureDate().compareTo(ticket.getDepartureDate())==0){
                ticketEntities.add(tickets.get(i));
            }
        }
        TicketEntity maxTicket=ticketEntities.get(0);
        for(int i=1;i<ticketEntities.size();i++){
            if(maxTicket.getSeat()<ticketEntities.get(i).getSeat()){
                maxTicket=ticketEntities.get(i);
            }
        }
        if(maxTicket.getSeat()>schedule.getTrain().getNumberOfSeats()){
            error="*All seats are busy";
        }
        return error;
    }
}
