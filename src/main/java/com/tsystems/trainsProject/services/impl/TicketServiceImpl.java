package com.tsystems.trainsProject.services.impl;

import com.tsystems.trainsProject.dao.impl.TicketDAOImpl;
import com.tsystems.trainsProject.models.BranchLineEntity;
import com.tsystems.trainsProject.models.ScheduleEntity;
import com.tsystems.trainsProject.models.TicketEntity;
import com.tsystems.trainsProject.services.TicketService;
import org.apache.commons.lang.time.DateUtils;
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
        List<Integer> firstLastStation = getFirstLastStationNumber(ticket);
        for (int j = 0; j < ticket.getSchedule().getTicket().size(); j++) {
            if (ticket.getSchedule().getTicket().get(j).getDepartureDate().compareTo(ticket.getDepartureDate()) == 0) {
                List<Integer> flstation = getFirstLastStationNumber(ticket.getSchedule().getTicket().get(j));
                if (!(flstation.get(1) <= firstLastStation.get(0) || flstation.get(0) >= firstLastStation.get(1))) {
                    tickets.add(ticket.getSchedule().getTicket().get(j));
                }
            }
        }
        List<TicketEntity> ticketEntityList = tickets;
        for (int j = 0; j < tickets.size() - 1; j++) {
            if (tickets.get(j).getSeat() == tickets.get(j + 1).getSeat()) {
                ticketEntityList.remove(tickets.get(j));
            }
        }
        int i = 0;
        boolean ok = true;
        while (i < tickets.size() && ok) {
            if (tickets.get(i).getSeat() != i + 1 && i + 1 <= maxNumber) {
                numberOfSeat = i + 1;
                ok = false;
            } else {
                i++;
            }
        }
        if (ok && tickets.size() + 1 <= maxNumber) {
            numberOfSeat = tickets.size() + 1;
        }
        return numberOfSeat;
    }

    private List<Integer> getFirstLastStationNumber(TicketEntity ticket) {
        List<Integer> numbers = new ArrayList<>();
        BranchLineEntity branch = ticket.getSchedule().getBranch();
        int numberFirstStation = 0;
        int numberLastStation = 0;
        for (int i = 0; i < branch.getDetailedInf().size(); i++) {
            if (branch.getDetailedInf().get(i).getStation().getIdStation() == ticket.getFirstStation().getIdStation()) {
                numberFirstStation = branch.getDetailedInf().get(i).getStationSerialNumber();
            }
            if (branch.getDetailedInf().get(i).getStation().getIdStation() == ticket.getLastStation().getIdStation()) {
                numberLastStation = branch.getDetailedInf().get(i).getStationSerialNumber();
            }
        }
        numbers.add(numberFirstStation);
        numbers.add(numberLastStation);
        return numbers;
    }

    @Override
    public boolean checkTime(TicketEntity ticket){
        Date date = new Date();
        int minutesNow = date.getHours()*60+date.getMinutes()+10;
        int minutesDep=ticket.getDepartureTime().getHours()*60+ticket.getDepartureTime().getMinutes();
        boolean ok2 = true;
        if ((DateUtils.isSameDay(ticket.getDepartureDate(), date) && minutesNow<minutesDep)
                || ticket.getDepartureDate().after(date)) {
            ok2 = true;
        } else {
            ok2 = false;
        }
        return ok2;
    }

    @Override
    public void delete1(TicketEntity ticketEntity) {
        List<TicketEntity> tickets = ticketDAO.findAllTickets();
        List<TicketEntity> ticketsToDelete = new ArrayList<>();
        for (int i = 0; i < tickets.size(); i++) {
            if (tickets.get(i).getSeat() == -1 && tickets.get(i).getIdTicket() != ticketEntity.getIdTicket()) {
                ticketsToDelete.add(tickets.get(i));
            }
        }
        for (int i = 0; i < ticketsToDelete.size(); i++) {
            ticketDAO.delete(ticketsToDelete.get(i));
        }
    }
}
