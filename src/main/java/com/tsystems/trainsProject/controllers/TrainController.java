package com.tsystems.trainsProject.controllers;

import com.tsystems.trainsProject.models.*;
import com.tsystems.trainsProject.services.StationService;
import com.tsystems.trainsProject.services.TrainService;
import com.tsystems.trainsProject.services.impl.SearchTrain;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Controller
public class TrainController {

    @Autowired
    TrainService trainService;

    @Autowired
    SearchTrain searchService;

    @Autowired
    StationService stationService;

    @RequestMapping(value = "/trains", method = RequestMethod.GET)
    public ModelAndView getAllTrains() {
        ModelAndView model = new ModelAndView();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        System.out.println(auth.getName());
        List<TrainEntity> trains = trainService.findAllTrains();
        int trainsCount = trains.size();
        model.addObject(trainsCount);
        model.addObject("trains", trains);
        model.setViewName("trains");
        return model;
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ModelAndView getFirstPage(@ModelAttribute Search search) {
        ModelAndView modelAndView = new ModelAndView();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        modelAndView.addObject("search",search);
        List<StationEntity> stations = stationService.findAllStations();
        modelAndView.addObject("stations",stations);
        modelAndView.setViewName("index");
        modelAndView.addObject("auth",auth.getName());
        return modelAndView;
    }


    @RequestMapping(value = "/", method = RequestMethod.POST)
    public String findTrainsInSchedule(@ModelAttribute Search search,
                                       BindingResult bindingResult,
                                       Model model) throws ParseException {
        Map<ScheduleEntity,List<Date>> variants = searchService.search(search,bindingResult);
        List<TicketEntity> tickets = new ArrayList<>();
        for (Map.Entry<ScheduleEntity, List<Date>> entry : variants.entrySet()) {
            TicketEntity ticket = new TicketEntity();
            ticket.setSchedule(entry.getKey());
            ticket.setDepartureTime(entry.getValue().get(0));
            ticket.setArrivalTime(entry.getValue().get(1));
            StationEntity station1 = stationService.findByName(search.getFirstStation());
            ticket.setFirstStation(station1);
            StationEntity station2 = stationService.findByName(search.getLastStation());
            ticket.setLastStation(station2);
            int difference = (ticket.getArrivalTime().getHours()*60+ticket.getArrivalTime().getMinutes())-(ticket.getDepartureTime().getHours()*60+ticket.getDepartureTime().getMinutes());
            ticket.setJourneyTime(strToTime(intToTime(difference)));
            tickets.add(ticket);
        }
        model.addAttribute("tickets",tickets);
        return "variants";
    }

    private String intToTime(int intTime){
        String startTime = "00:00";
        int h = intTime / 60 + Integer.parseInt(startTime.substring(0,1));
        int m = intTime % 60 + Integer.parseInt(startTime.substring(3,4));
        String newtime = h+":"+m;
        return newtime;
    }

    private Date strToTime(String strTime) throws ParseException {
        DateFormat df = new SimpleDateFormat("hh:mm");
        Date result =  df.parse(strTime);
        return result;
    }




}
