package com.tsystems.trainsProject.controllers;

import com.tsystems.trainsProject.dto.Search;
import com.tsystems.trainsProject.models.*;
import com.tsystems.trainsProject.services.StationService;
import com.tsystems.trainsProject.services.TicketService;
import com.tsystems.trainsProject.services.TrainService;
import com.tsystems.trainsProject.services.UserService;
import com.tsystems.trainsProject.services.impl.SearchTrain;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.*;

@Controller
public class TrainController {

    @Autowired
    TrainService trainService;

    @Autowired
    StationService stationService;

    @Autowired
    TicketService ticketService;

    @Autowired
    UserService userService;

    @RequestMapping(value = "/createTrain", method = RequestMethod.GET)
    public String getTrain(@ModelAttribute TrainEntity train, Model model) {
        String error = "";
        return create(train, model, error, "createTrain");
    }

    private String create(TrainEntity train, Model model, String error, String type) {
        model.addAttribute("error", error);
        model.addAttribute("train", train);
        model.addAttribute("type", type);
        return "editTrain";
    }

    @RequestMapping(value = "/createTrain", method = RequestMethod.POST)
    public String postTrain(@ModelAttribute TrainEntity train, Model model) {
        String error = trainService.checkUniqueTRainNumber(train);
        if (!error.equals("")) {
            return create(train, model, error, "createTrain");
        }
        trainService.saveOrUpdate(train);
        return "redirect:/trains";
    }

    @RequestMapping(value = "/updateTrain/{pk}", method = RequestMethod.GET)
    public ModelAndView updateTrain(@PathVariable Integer pk) {
        TrainEntity train = trainService.findById(pk);
        ModelAndView model = new ModelAndView();
        List<String> errors = new ArrayList<>();
        if (!train.getSchedule().isEmpty()) {
            errors.add("*You can't update this train. It is in the schedule");
            return getAllStationsHelp(errors);
        } else {
            String error = "";
            model.addObject("errors", error);
            model.addObject("train", train);
            model.addObject("type", "updateTrain");
            model.setViewName("editTrain");
            return model;
        }
    }


    @RequestMapping(value = "/updateTrain", method = RequestMethod.POST)
    public String update(@ModelAttribute TrainEntity train, Model model) {
        String error = trainService.checkUniqueTRainNumber(train);
        if (!error.equals("")) {
            return create(train, model, error, "updateTrain");
        }
        trainService.saveOrUpdate(train);
        return "redirect:/trains";
    }

    @RequestMapping(value = "/deleteTrain/{pk}", method = RequestMethod.GET)
    public ModelAndView deleteTrain(@PathVariable Integer pk) {
        TrainEntity train = trainService.findById(pk);
        ModelAndView model = new ModelAndView();
        List<String> errors = new ArrayList<>();
        if (train.getSchedule().isEmpty()) {
            trainService.delete(train);
            model = getAllStationsHelp(errors);
        } else {
            errors.add("*You can't delete this train. It is in the schedule");
            model = getAllStationsHelp(errors);
        }

        return model;
    }

    @RequestMapping(value = "/trains", method = RequestMethod.GET)
    public ModelAndView getAllTrains() {
        List<String> errors = new ArrayList<>();
        return getAllStationsHelp(errors);
    }

    private ModelAndView getAllStationsHelp(List<String> errors) {
        ModelAndView model = new ModelAndView();
        List<TrainEntity> trains = trainService.findAllTrains();
        model.addObject("trains", trains);
        model.addObject("errors", errors);
        model.setViewName("trains");
        return model;
    }

}
