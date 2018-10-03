package com.tsystems.trainsProject.controllers;

import com.tsystems.trainsProject.models.TrainEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class MainController {
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public ModelAndView getAdd() {
        ModelAndView model = new ModelAndView();
        model.setViewName("login");
        return model;
    }

}