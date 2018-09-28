package com.tsystems.trainsProject.controllers;

import com.tsystems.trainsProject.models.TrainEntity;
import com.tsystems.trainsProject.services.TrainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import java.util.ArrayList;
import java.util.List;

@Controller
public class TrainController {

    @Autowired
    TrainService trainService;

    @RequestMapping(value = "/trains", method = RequestMethod.GET)
    public ModelAndView getAdd() {
        ModelAndView model = new ModelAndView();
        List<TrainEntity> trains = trainService.findAllTrains();
        int trainsCount = trains.size();
        model.addObject(trainsCount);
        model.addObject("trains", trains);
        model.setViewName("trains");
        return model;
    }
}
