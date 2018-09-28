package com.tsystems.trainsProject.controllers;

import com.tsystems.trainsProject.models.UserEntity;
import com.tsystems.trainsProject.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

@Controller
public class MainController {

    @Autowired
    UserService userService;

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ModelAndView getAdd() {
        ModelAndView model = new ModelAndView();
        List<UserEntity> user = new ArrayList<>();
        user=userService.findAllUsers();
        model.addObject("user", user);
        model.setViewName("home"        );
        return model;
    }


}