package com.tsystems.trainsProject.controllers;

import com.mysql.cj.xdevapi.Collection;
import com.tsystems.trainsProject.models.*;
import com.tsystems.trainsProject.services.*;
import com.tsystems.trainsProject.services.impl.PassangerServiceImpl;
import com.tsystems.trainsProject.services.impl.TicketServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@Controller
public class TicketController {

    @Autowired
    TicketService ticketService;

    @Autowired
    PassangerService passangerService;

    @Autowired
    UserService userService;

    @Autowired
    ScheduleService scheduleService;

    @Autowired
    StationService station;

    @RequestMapping(value = "/seeTickets/{pk}", method = RequestMethod.GET)
    public String getTickets(@PathVariable Integer pk, Model model) {
        ScheduleEntity schedule = scheduleService.findById(pk);
        List<TicketEntity> tickets = schedule.getTicket();
        model.addAttribute("tickets", tickets);
        return "tickets";
    }

    @RequestMapping(value = "/seeTicket/{pk}", method = RequestMethod.GET)
    public String getTicketById(@PathVariable Integer pk, Model model) {
        TicketEntity ticket = ticketService.findById(pk);
        model.addAttribute("ticket", ticket);
        return "ticket";
    }

    @RequestMapping(value = "/chooseTicket/{pk}", method = RequestMethod.GET)
    public String getTicket(@PathVariable Integer pk, Model model) {
        TicketEntity ticket = ticketService.findById(pk);
        model.addAttribute("ticket", ticket);
        return "inputDate";
    }

    @RequestMapping(value = "/tickets", method = RequestMethod.GET)
    public String getUserTicket(Model model) {
        UserEntity user = userService.findByLogin(SecurityContextHolder.getContext().getAuthentication().getName());
        List<PassangerEntity> passangers = user.getPassanger();
        List<TicketEntity> tickets = new ArrayList<>();
        for (int i = 0; i < passangers.size(); i++) {
            tickets.addAll(passangers.get(i).getTickets());
        }
        RoleEntity role = new RoleEntity();
        role = user.getRole();
        model.addAttribute("role", role);
        model.addAttribute("tickets", tickets);
        return "tickets";
    }


    @RequestMapping(value = "/chooseTicket", method = RequestMethod.POST)
    public String postTIcket(@ModelAttribute TicketEntity ticket, Model model)  {
        ticketService.delete1(ticket);
        PassangerEntity passanger = ticket.getPassanger();
        boolean timeCheck = ticketService.checkTime(ticket);
        Date date = new Date();
        if (ticket.getDepartureDate().after(date) || timeCheck) {
            ticket.setSchedule(scheduleService.findById(ticket.getSchedule().getIdSchedule()));
            ticket.setLastStation(station.findById(ticket.getLastStation().getIdStation()));
            ticket.setFirstStation(station.findById(ticket.getFirstStation().getIdStation()));
            List<PassangerEntity> allPassangersOnTRain = new ArrayList<>();
            List<TicketEntity> tickets = ticket.getSchedule().getTicket();
            tickets.remove(ticket);
            if (!tickets.isEmpty()) {
                for (int i = 0; i < tickets.size(); i++) {
                    if (tickets.get(i).getDepartureDate().compareTo(ticket.getDepartureDate()) == 0) {
                        allPassangersOnTRain.add(tickets.get(i).getPassanger());
                    }
                }
            }
            if (passangerService.checkTheEqualtyPassanger(passanger, allPassangersOnTRain)) {
                UserEntity user = userService.findByLogin(SecurityContextHolder.getContext().getAuthentication().getName());
                int id = passangerService.saveOrUpdate(passanger);
                PassangerEntity passangerEntity = passangerService.findById(id);
                passangerEntity.setUser(user);
                ticket.setPassanger(passangerEntity);
                if (ticketService.findSeatWithMaxNumber(ticket) != 0) {
                    ticket.setSeat(ticketService.findSeatWithMaxNumber(ticket));
                    ticketService.saveOrUpdate(ticket);
                    return "redirect:/";
                } else {
                    String error = "*All seats are busy";
                    model.addAttribute("error", error);
                    model.addAttribute("ticket", ticket);
                    return "inputDate";
                }
            } else {
                String error = "*Passanger has already had a ticket at this train.";
                model.addAttribute("error", error);
                model.addAttribute("ticket", ticket);
                return "inputDate";
            }
        } else {
            String error = "*You can't buy ticket after the train departure";
            model.addAttribute("error", error);
            model.addAttribute("ticket", ticket);
            return "inputDate";
        }
    }
}
