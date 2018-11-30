package com.tsystems.trainsProject.controllers;

import com.tsystems.trainsProject.dto.Search;
import com.tsystems.trainsProject.models.StationEntity;
import com.tsystems.trainsProject.models.UserEntity;
import com.tsystems.trainsProject.services.StationService;
import com.tsystems.trainsProject.services.UserService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.context.ApplicationContext;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@Controller
public class MainController {

    @Autowired
    private ApplicationContext appContext;


    private static final Logger logger = Logger.getLogger(MainController.class);
    @Autowired
    StationService stationService;

    @Autowired
    UserService userService;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String getFirstPage(@ModelAttribute Search search, Model model) {
        String result = "";
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        logger.info(auth.getName());
        UserEntity user = userService.findByLogin(auth.getName());
        if (user == null || user.getRole().getTitle().equals("USER")) {
            result = userActions(model, auth, search);
        } else {
            if (user.getRole().getTitle().equals("WORKER")) {
                result = "workerMainPage";
            }
        }
        return result;
    }

    private String userActions(Model modelAndView, Authentication auth, Search search) {
        modelAndView.addAttribute("search", search);
        List<StationEntity> stations = stationService.findAllStations();
        modelAndView.addAttribute("stations", stations);
        modelAndView.addAttribute("auth", auth.getName());
        return "index";
    }
}