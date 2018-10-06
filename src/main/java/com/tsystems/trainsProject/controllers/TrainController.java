package com.tsystems.trainsProject.controllers;

import com.tsystems.trainsProject.models.*;
import com.tsystems.trainsProject.services.InfBranchService;
import com.tsystems.trainsProject.services.ScheduleService;
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

import java.sql.Time;
import java.sql.Timestamp;
import java.text.ParseException;
import java.util.ArrayList;
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
                                       Model model)  {
        List<ScheduleEntity> variants = searchService.search(search,bindingResult);
        model.addAttribute("variants",variants);
        return "variants";
    }




}
