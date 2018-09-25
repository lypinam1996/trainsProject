package com.tsystems.trainsProject.controllers;

import com.tsystems.trainsProject.models.UsersEntity;
import com.tsystems.trainsProject.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@Controller
public class MainController {

    @Autowired
    UserService userService;

    @RequestMapping(value = "/list", method = RequestMethod.POST)
    public String getAdd(Model model) {
        List<UsersEntity> user = userService.findAllUsers();
        model.addAttribute("user", user);
        return "Home";
    }
}