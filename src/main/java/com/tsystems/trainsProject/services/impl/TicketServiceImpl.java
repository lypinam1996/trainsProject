package com.tsystems.trainsProject.services.impl;

import com.tsystems.trainsProject.dao.PassangerDAO;
import com.tsystems.trainsProject.dao.impl.TicketDAOImpl;
import com.tsystems.trainsProject.models.BranchLineEntity;
import com.tsystems.trainsProject.models.PassangerEntity;
import com.tsystems.trainsProject.models.TicketEntity;
import com.tsystems.trainsProject.services.TicketService;
import org.apache.commons.lang.time.DateUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service("TicketService")
@Transactional
public class TicketServiceImpl implements TicketService {

    private static final Logger logger = Logger.getLogger(TicketServiceImpl.class);

    @Autowired
    TicketDAOImpl ticketDAO;

    @Autowired
    PassangerDAO passangerDAO;

    @Override
    public List<TicketEntity> findAllTickets() {
        return ticketDAO.findAllTickets();
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
    public List<TicketEntity> findByDate(Date today) {
        return ticketDAO.findByDate(today);
    }

    @Override
    public void deleteOldTickets() {
        logger.info("TicketServiceImpl: start to delete tickets");
        List<TicketEntity> tickets = findAllTickets();
        if (tickets.size() != 0) {
            Calendar cal = Calendar.getInstance();
            cal.add(Calendar.DATE, -1);
            Date yesterday = cal.getTime();
            for (int i = 0; i < tickets.size(); i++) {
                if (tickets.get(i).getDepartureDate().before(yesterday)) {
                    PassangerEntity passangerEntity = tickets.get(i).getPassanger();
                    ticketDAO.delete(tickets.get(i));
                    passangerDAO.delete(passangerEntity);
                }
            }
        }
        logger.info("TicketServiceImpl: tickets have been deleted");
    }

    @Override
    public int findSeatWithMaxNumber(TicketEntity ticket) {
        logger.info("TicketServiceImpl: start find seat with max number");
        int numberOfSeat = 0;
        int maxNumber = ticket.getSchedule().getTrain().getNumberOfSeats();
        List<TicketEntity> tickets = new ArrayList<>();
        List<Integer> firstLastStation = getFirstLastStationNumber(ticket);
        for (int j = 0; j < ticket.getSchedule().getTicket().size(); j++) {
            if (ticket.getSchedule().getTicket().get(j).getDepartureDate().compareTo(ticket.getDepartureDate()) == 0) {
                List<Integer> flstation = getFirstLastStationNumber(ticket.getSchedule().getTicket().get(j));
                if ((flstation.get(0) <= firstLastStation.get(0) && flstation.get(1) >= firstLastStation.get(1)) ||
                (flstation.get(0) >= firstLastStation.get(0) && flstation.get(1) <= firstLastStation.get(1)))
                {
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
            if (tickets.get(i).getSeat() != i + 1 && i + 1 < maxNumber) {
                numberOfSeat = i + 1;
                ok = false;
            } else {
                i++;
            }
        }
        if (ok && tickets.size() + 1 <= maxNumber) {
            numberOfSeat = tickets.size() + 1;
        }
        logger.info("TicketServiceImpl:seat has been found");
        return numberOfSeat;
    }

    private List<Integer> getFirstLastStationNumber(TicketEntity ticket) {
        logger.info("TicketServiceImpl: start to find stations numbers");
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
        logger.info("TicketServiceImpl:numbers have been found");
        return numbers;
    }


    private String checkTime(TicketEntity ticket) {
        logger.info("TicketServiceImpl: start to check time");
        Date date = new Date();
        LocalTime minutesNow = LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault()).toLocalTime();
        minutesNow = minutesNow.plusMinutes(10);
        LocalTime minutesDep = LocalDateTime.ofInstant(ticket.getDepartureTime().toInstant(), ZoneId.systemDefault()).toLocalTime();
        if ((DateUtils.isSameDay(ticket.getDepartureDate(), date) && minutesNow.isBefore(minutesDep))
                || ticket.getDepartureDate().after(date)) {
            logger.info("TicketServiceImpl: time has been checked");
            return "";
        } else {
            logger.info("TicketServiceImpl: time has been checked");
            return "*You can't buy ticket after the train departure";
        }

    }

    private String checkPassanger(TicketEntity ticket){
        if(ticket.getPassanger().getName()=="" ||
                ticket.getPassanger().getSurname()=="" ||
                ticket.getPassanger().getDateOfBirth()==null ||
                ticket.getDepartureDate()==null){
            return "*Some fields was missed.";
        }
        else {
            return "";
        }
    }

    private String checkTheEqualtyPassanger(TicketEntity ticket) {
        PassangerEntity passanger = ticket.getPassanger();
        logger.info("TicketServiceImpl: check the equalty of passanger");
        List<PassangerEntity> allPassangers = new ArrayList<>();
        List<TicketEntity> tickets = ticket.getSchedule().getTicket();
        tickets.remove(ticket);
        if (!tickets.isEmpty()) {
            for (int i = 0; i < tickets.size(); i++) {
                if (tickets.get(i).getDepartureDate().compareTo(ticket.getDepartureDate()) == 0) {
                    allPassangers.add(tickets.get(i).getPassanger());
                }
            }
        }
        boolean ok = true;
        if (!allPassangers.isEmpty()) {
            int i = 0;
            while (i < allPassangers.size() && ok) {
                if (allPassangers.get(i).getDateOfBirth() == null && passanger.getDateOfBirth() == null) {
                    if (allPassangers.get(i).getName().equals(passanger.getName()) &&
                            allPassangers.get(i).getSurname().equals(passanger.getSurname()) &&
                            allPassangers.get(i).getPatronymic().equals(passanger.getPatronymic())) {
                        ok = false;
                    } else {
                        i++;
                    }
                } else {
                    if (allPassangers.get(i).getDateOfBirth() != null && passanger.getDateOfBirth() != null) {
                        if (DateUtils.isSameInstant(allPassangers.get(i).getDateOfBirth(), passanger.getDateOfBirth()) &&
                                allPassangers.get(i).getName().equals(passanger.getName()) &&
                                allPassangers.get(i).getSurname().equals(passanger.getSurname()) &&
                                allPassangers.get(i).getPatronymic().equals(passanger.getPatronymic())) {
                            ok = false;
                        } else {
                            i++;
                        }
                    } else {
                        i++;
                    }
                }
            }
        }
        if (ok){
            logger.info("TicketServiceImpl: branch has been checked");
            return "";
        }
        else {
            logger.info("TicketServiceImpl: branch has been checked");
            return "*Passanger has already had a ticket at this train.";
        }
    }

    @Override
    public BindingResult validation(BindingResult bindingResult,TicketEntity ticket){
        if(ticket.getSchedule().getProhibitPurchase()==null || ticket.getSchedule().getProhibitPurchase().before(new Date())){
            ObjectError objectError = new ObjectError("departureDate",
                    "You temporary can't buy a ticket on this train.");
            bindingResult.addError(objectError);
        }
        String errorCheckTime=checkTime(ticket);
        if (errorCheckTime !="") {
            ObjectError objectError = new ObjectError("departureDate",
                    errorCheckTime);
            bindingResult.addError(objectError);
        }
        String errorCheckPassanger=checkPassanger(ticket);
        if (errorCheckPassanger !="") {
            ObjectError objectError = new ObjectError("departureDate",
                    errorCheckPassanger);
            bindingResult.addError(objectError);
        }
        String errorTheEqualtyPassanger=checkTheEqualtyPassanger(ticket);
        if (errorTheEqualtyPassanger !="") {
            ObjectError objectError = new ObjectError("departureDate",
                    errorTheEqualtyPassanger);
            bindingResult.addError(objectError);
        }
        return bindingResult;
    }
}
