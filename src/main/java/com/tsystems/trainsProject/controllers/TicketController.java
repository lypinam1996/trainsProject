package com.tsystems.trainsProject.controllers;

import com.tsystems.trainsProject.models.PassangerEntity;
import com.tsystems.trainsProject.models.ScheduleEntity;
import com.tsystems.trainsProject.models.TicketEntity;
import com.tsystems.trainsProject.models.UserEntity;
import com.tsystems.trainsProject.services.PassangerService;
import com.tsystems.trainsProject.services.TicketService;
import com.tsystems.trainsProject.services.UserService;
import com.tsystems.trainsProject.services.impl.PassangerServiceImpl;
import com.tsystems.trainsProject.services.impl.TicketServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.ArrayList;
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

    @RequestMapping(value = "/chooseTicket/{pk}", method = RequestMethod.GET)
    public String getTicket(@PathVariable Integer pk,Model model) {
        TicketEntity ticket = ticketService.findById(pk);
        model.addAttribute("ticket",ticket);
        return "inputDate";
    }

    @RequestMapping(value = "/chooseTicket", method = RequestMethod.POST)
    public String postTIcket( @ModelAttribute TicketEntity ticket, Model model) {
        PassangerEntity passanger = ticket.getPassanger();
        if(passangerService.checkTheEqualtyPassanger(passanger)) {
            Date date = new Date();
            if(ticket.getDepartureTime().getHours()*60+ticket.getDepartureTime().getMinutes()-
                    date.getHours()*60+date.getMinutes()<10) {
                if(ticketService.checkNumberOfTicket(ticket).equals("")) {
                    UserEntity user = userService.findByLogin(SecurityContextHolder.getContext().getAuthentication().getName());
                    passanger.setUser(user);
                    int id = passangerService.saveOrUpdate(passanger);
                    PassangerEntity passangerEntity = passangerService.findById(id);
                    ticket.setPassanger(passangerEntity);
                    ticketService.saveOrUpdate(ticket);
                    return "redirect:/";
                }
                else {
                    String error = ticketService.checkNumberOfTicket(ticket);
                    model.addAttribute("error",error);
                    model.addAttribute("ticket",ticket);
                    return "inputDate";
                }
            }
            else{
                String error = "*You can't buy ticket after ////";
                model.addAttribute("error",error);
                model.addAttribute("ticket",ticket);
                return "inputDate";
            }
        }
        else {
            String error = "*Passanger has already had a ticket at this train.";
            model.addAttribute("error",error);
            model.addAttribute("ticket",ticket);
            return "inputDate";
        }
    }
}
