package com.tsystems.trainsProject.controllers;

import com.tsystems.trainsProject.dto.Converter;
import com.tsystems.trainsProject.dto.Search;
import com.tsystems.trainsProject.dto.SingletonDto;
import com.tsystems.trainsProject.dto.VariantDto;
import com.tsystems.trainsProject.models.*;
import com.tsystems.trainsProject.services.*;
import com.tsystems.trainsProject.services.impl.SearchTrain;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.*;

@Controller
public class TicketController {

    private static final Logger logger = Logger.getLogger(TicketController.class);

    SingletonDto singletonDto;

    @Autowired
    TicketService ticketService;

    @Autowired
    PassangerService passangerService;

    @Autowired
    UserService userService;

    @Autowired
    ScheduleService scheduleService;

    @Autowired
    StationService stationService;

    @Autowired
    SearchTrain searchService;

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public String findTrainsInSchedule(@ModelAttribute Search search,
                                       BindingResult bindingResult,
                                       Model model) {
        logger.info("TicketController: start to find variants");
        Map<ScheduleEntity, List<Date>> variants = searchService.search(search, bindingResult);
        List<VariantDto> variant = new ArrayList<>();
        singletonDto = SingletonDto.getInstance();
        List<VariantDto> savedVariants = singletonDto.getVariants();
        for (Map.Entry<ScheduleEntity, List<Date>> entry : variants.entrySet()) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(entry.getValue().get(1));
            calendar.add(Calendar.HOUR, -entry.getValue().get(0).getHours());
            calendar.add(Calendar.MINUTE, -entry.getValue().get(0).getMinutes());
            VariantDto variantDto = new VariantDto(0, entry.getValue().get(0),
                    entry.getValue().get(1), entry.getKey(), stationService.findByName(search.getFirstStation()),
                    stationService.findByName(search.getLastStation()), calendar.getTime());
            if (savedVariants.size() != 0) {
                int id = findMaxId(savedVariants);
                variantDto.setIdVariant(id);
            } else {
                variantDto.setIdVariant(1);
            }
            variant.add(variantDto);
        }
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        RoleEntity role = new RoleEntity();
        if (!auth.getName().equals("anonymousUser")) {
            UserEntity user = userService.findByLogin(auth.getName());
            role = user.getRole();
        }
        singletonDto.setVariants(variant);
        model.addAttribute("role", role);
        model.addAttribute("tickets", variant);
        logger.info("TicketController: return variants");
        return "variants";
    }

    private int findMaxId(List<VariantDto> variants) {
        int result = 0;
        for (int i = 0; i < variants.size(); i++) {
            if (variants.get(i).getIdVariant() > result) {
                result = variants.get(i).getIdVariant();
            }
        }
        result++;
        return result;
    }

    @RequestMapping(value = "/seeTickets/{pk}", method = RequestMethod.GET)
    public String getTickets(@PathVariable Integer pk, Model model) {
        ScheduleEntity schedule = scheduleService.findById(pk);
        List<TicketEntity> tickets = schedule.getTicket();
        model.addAttribute("tickets", tickets);
        logger.info("TicketController: return tickets page");
        return "tickets";
    }

    @RequestMapping(value = "/seeTicket/{pk}", method = RequestMethod.GET)
    public String getTicketById(@PathVariable Integer pk, Model model) {
        TicketEntity ticket = ticketService.findById(pk);
        model.addAttribute("ticket", ticket);
        logger.info("TicketController: return ticket page");
        return "ticket";
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

    @RequestMapping(value = "/chooseTicket/{pk}", method = RequestMethod.GET)
    public String getTicket(@PathVariable Integer pk, Model model) {
        List<TicketEntity> tickets = ticketService.findAllTickets();
        singletonDto = SingletonDto.getInstance();
        List<VariantDto> variants = singletonDto.getVariants();
        VariantDto variant = searchService.getVariant(pk, variants);
        Converter converter = new Converter();
        TicketEntity ticket = converter.convertVariantToTicket(variant, tickets);
        model.addAttribute("ticket", ticket);
        logger.info("TicketController: return create ticket page");
        return "inputDate";
    }

    @RequestMapping(value = "/chooseTicket", method = RequestMethod.POST)
    public String postTicket(@ModelAttribute("ticket") @Validated TicketEntity ticket,
                             Model model, BindingResult bindingResult) {
        logger.info("TicketController: start to save");
        ticket.setSchedule(scheduleService.findById(ticket.getSchedule().getIdSchedule()));
        ticket.setLastStation(stationService.findById(ticket.getLastStation().getIdStation()));
        ticket.setFirstStation(stationService.findById(ticket.getFirstStation().getIdStation()));
        bindingResult = ticketService.validation(bindingResult, ticket);
        if (bindingResult.hasErrors()) {
            model.addAttribute("ticket", ticket);
            return "inputDate";
        } else {
            UserEntity user = userService.findByLogin(SecurityContextHolder.getContext().getAuthentication().getName());
            ticket.getPassanger().setUser(user);
            if (ticketService.findSeatWithMaxNumber(ticket) != 0) {
                ticket.setSeat(ticketService.findSeatWithMaxNumber(ticket));
                ticketService.saveOrUpdate(ticket);
                logger.info("TicketController:  ticket has been saved");
                return "redirect:/";
            } else {
                logger.info("TicketController: there are some validation problems in ticket");
                String error = "*All seats are busy";
                model.addAttribute("error", error);
                model.addAttribute("ticket", ticket);
                return "inputDate";
            }
        }
    }
}
