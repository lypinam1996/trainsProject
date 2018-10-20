package com.tsystems.trainsProject.services.impl;

import com.tsystems.trainsProject.dao.impl.TicketDAOImpl;
import com.tsystems.trainsProject.models.ScheduleEntity;
import com.tsystems.trainsProject.models.TicketEntity;
import com.tsystems.trainsProject.services.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
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

    @Override
    public String checkNumberOfTicket(TicketEntity ticket) {
        String error = "";
        List<TicketEntity> ticketEntities = new ArrayList<>();
        ScheduleEntity schedule = ticket.getSchedule();
        List<TicketEntity> tickets = schedule.getTicket();
        tickets.remove(ticket);
        if (!tickets.isEmpty()) {
            for (int i = 0; i < tickets.size(); i++) {
                if (tickets.get(i).getDepartureDate().compareTo(ticket.getDepartureDate()) == 0) {
                    ticketEntities.add(tickets.get(i));
                }
            }
            if (!ticketEntities.isEmpty()) {
                TicketEntity maxTicket = ticketEntities.get(0);
                if (ticketEntities.size() > 1) {
                    for (int i = 1; i < ticketEntities.size(); i++) {
                        if (maxTicket.getSeat() < ticketEntities.get(i).getSeat()) {
                            maxTicket = ticketEntities.get(i);
                        }
                    }
                }
                if (maxTicket.getSeat() >= schedule.getTrain().getNumberOfSeats()) {
                    error = "*All seats are busy";
                }
            }
        }
        return error;
    }

    @Override
    public int findSeatWithMaxNumber(TicketEntity ticket) {
        int numberOfSeat = 0;
        int maxNumber = ticket.getSchedule().getTrain().getNumberOfSeats();
        List<TicketEntity> tickets = new ArrayList<>();
        for (int j = 0; j < ticket.getSchedule().getTicket().size(); j++) {
            if (ticket.getSchedule().getTicket().get(j).getDepartureDate().compareTo(ticket.getDepartureDate()) == 0) {
                tickets.add(ticket.getSchedule().getTicket().get(j));
            }
        }
        int i = 0;
        boolean ok = true;
        while (i < tickets.size()) {
            if (tickets.get(i).getSeat() != i+1 && i+1<=maxNumber) {
                numberOfSeat = i+1;
                ok = false;
            } else {
                i++;
            }
        }
        if (ok && tickets.size()+1<=maxNumber) {
            numberOfSeat = tickets.size() + 1;
        }
        return numberOfSeat;
    }

    @Override
    public boolean checkTime(TicketEntity ticket){
        Date date = new Date();
        boolean equality = ticket.getDepartureDate().getYear() == date.getYear() &&
                ticket.getDepartureDate().getMonth() == date.getMonth() &&
                ticket.getDepartureDate().getDay() == date.getDay();
        boolean ok2 = true;
        if (equality) {
            int diff = ticket.getDepartureTime().getHours() * 60 + ticket.getDepartureTime().getMinutes() -
                    date.getHours() * 60 + date.getMinutes();
            if (diff > 10) {
                ok2 = true;
            } else {
                ok2 = false;
            }
        }
        return ok2;
    }
    @Override
    public void delete1(TicketEntity ticketEntity){
        List<TicketEntity> tickets = ticketDAO.findAllTickets();
        List<TicketEntity> ticketsToDelete = new ArrayList<>();
        for(int i=0;i<tickets.size();i++){
            if (tickets.get(i).getSeat()==-1 && tickets.get(i).getIdTicket()!=ticketEntity.getIdTicket()){
              ticketsToDelete.add(tickets.get(i));
            }
        }
        for(int i=0;i<ticketsToDelete.size();i++){
            ticketDAO.delete(ticketsToDelete.get(i));
        }
    }
}
