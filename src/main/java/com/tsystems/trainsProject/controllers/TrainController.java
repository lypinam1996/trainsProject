package com.tsystems.trainsProject.controllers;

import com.tsystems.trainsProject.models.TrainEntity;
import com.tsystems.trainsProject.services.StationService;
import com.tsystems.trainsProject.services.TicketService;
import com.tsystems.trainsProject.services.TrainService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

@Controller
public class TrainController {
    private static final Logger logger = Logger.getLogger(TrainController.class);

    @Autowired
    TrainService trainService;

    @Autowired
    StationService stationService;

    @Autowired
    TicketService ticketService;

//    @Autowired
//    UserService userService;

    @RequestMapping(value = "/createTrain", method = RequestMethod.GET)
    public String getTrain(@ModelAttribute TrainEntity train, Model model) {
        logger.info("TrainController: return create train page");
        return create(train, model, "createTrain");
    }

    private String create(TrainEntity train, Model model, String type) {
        model.addAttribute("train", train);
        model.addAttribute("type", type);
        return "editTrain";
    }

    @RequestMapping(value = "/createTrain", method = RequestMethod.POST)
    public String postTrain(@ModelAttribute("train") @Validated TrainEntity train,
                Model model,  BindingResult bindingResult) {
        logger.info("TrainController: start to save train");
        bindingResult= trainService.checkUniqueTrainNumber(train,bindingResult);
        if (bindingResult.hasErrors()) {
            return create(train, model, "createTrain");
        }
        trainService.saveOrUpdate(train);
        logger.info("TrainController:train has been saved");
        return "redirect:/trains";
    }

    @RequestMapping(value = "/updateTrain/{pk}", method = RequestMethod.GET)
    public ModelAndView updateTrain(@PathVariable Integer pk) {
        TrainEntity train = trainService.findById(pk);
        ModelAndView model = new ModelAndView();
        List<String> errors = new ArrayList<>();
        if (!train.getSchedule().isEmpty()) {
            logger.info("TrainController:there are some validation problems in train");
            errors.add("*You can't update this train. It is in the schedule");
            return getAllTrainsHelp(errors);
        } else {
            String error = "";
            model.addObject("errors", error);
            model.addObject("train", train);
            model.addObject("type", "updateTrain");
            model.setViewName("editTrain");
            logger.info("TrainController:return update train page");
            return model;
        }
    }

    @RequestMapping(value = "/updateTrain", method = RequestMethod.POST)
    public String update(@ModelAttribute("train") @Validated TrainEntity train,
                         Model model,  BindingResult bindingResult) {
        logger.info("TrainController:start to update train");
        bindingResult= trainService.checkUniqueTrainNumber(train,bindingResult);
        if (bindingResult.hasErrors()) {
            logger.info("TrainController:there are some validation problems in train");
            return create(train, model, "updateTrain");
        }
        trainService.saveOrUpdate(train);
        logger.info("TrainController:train has been updated");
        return "redirect:/trains";
    }

    @RequestMapping(value = "/deleteTrain/{pk}", method = RequestMethod.GET)
    public ModelAndView deleteTrain(@PathVariable Integer pk) {
        logger.info("TrainController:start to delete train");
        TrainEntity train = trainService.findById(pk);
        ModelAndView model = new ModelAndView();
        List<String> errors = new ArrayList<>();
        if (train.getSchedule().isEmpty()) {
            trainService.delete(train);
            model = getAllTrainsHelp(errors);
            logger.info("TrainController:train has been deleted");
        } else {
            errors.add("*You can't delete this train. It is in the schedule");
            model = getAllTrainsHelp(errors);
            logger.info("TrainController:there are some validation problems in train");
        }
        return model;
    }

    @RequestMapping(value = "/trains", method = RequestMethod.GET)
    public ModelAndView getAllTrains() {
        List<String> errors = new ArrayList<>();
        logger.info("TrainController:return trains page");
        return getAllTrainsHelp(errors);
    }

    private ModelAndView getAllTrainsHelp(List<String> errors) {
        ModelAndView model = new ModelAndView();
        List<TrainEntity> trains = trainService.findAllTrains();
        model.addObject("trains", trains);
        model.addObject("errors", errors);
        model.setViewName("trains");
        return model;
    }

}
