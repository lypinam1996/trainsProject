package com.tsystems.trainsProject.controllers;

import com.tsystems.trainsProject.config.JwtRequestFilter;
import com.tsystems.trainsProject.dto.Search;
import com.tsystems.trainsProject.models.RoleEntity;
import com.tsystems.trainsProject.models.StationEntity;
import com.tsystems.trainsProject.models.UserEntity;
import com.tsystems.trainsProject.services.StationService;
import com.tsystems.trainsProject.services.UserService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;

import java.util.List;

@Controller
public class MainController
{

    private static final Logger logger = Logger.getLogger(MainController.class);

    @Autowired
    StationService              stationService;

    @Autowired
    UserService                 userService;
    @Autowired
    JwtRequestFilter            filter;

    @RequestMapping(value = "/home", method = RequestMethod.GET)
    public String getFirstPage(@ModelAttribute Search search, Model model)
    {
        String result = "";
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserEntity user = userService.findByLogin(auth.getName());
        if (user == null || user.getRole().getTitle().equals("USER"))
        {
            result = userActions(model, auth, search);
        }
        else
        {
            if (user.getRole().getTitle().equals("WORKER"))
            {
                result = "workerMainPage";
            }
        }
        logger.info("MainController: return first page");
        return result;
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public RedirectView getHome(@RequestParam String token)
    {
        filter.doFilterInternal(token);
        return new RedirectView("/home");
    }

    private String userActions(Model modelAndView, Authentication auth, Search search)
    {
        RoleEntity role = new RoleEntity();
        if (!auth.getName().equals("anonymousUser"))
        {
            UserEntity user = userService.findByLogin(auth.getName());
            role = user.getRole();
        }
        modelAndView.addAttribute("search", search);
        List<StationEntity> stations = stationService.findAllStations();
        modelAndView.addAttribute("stations", stations);
        modelAndView.addAttribute("auth", auth.getName());
        modelAndView.addAttribute("role", role);
        return "index";
    }
}
