package com.tsystems.trainsProject.controllers;

import com.tsystems.trainsProject.models.RoleEntity;
import com.tsystems.trainsProject.models.UserEntity;
import com.tsystems.trainsProject.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Controller
public class AuthenticationController {

    @Autowired
    UserService userService;

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public ModelAndView login() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("login");
        return modelAndView;
    }

    @RequestMapping(value = "/registration", method = RequestMethod.GET)
    public ModelAndView registration() {
        ModelAndView modelAndView = new ModelAndView();
        List<String> errors = new ArrayList<>();
        modelAndView.setViewName(getUser(modelAndView,errors));
        return modelAndView;
    }

    private String getUser(ModelAndView modelAndView, List<String> errors) {
        UserEntity user = new UserEntity();
        modelAndView.addObject("errors", errors);
        modelAndView.addObject("user", user);
        return "registration";
    }

    @RequestMapping(value = "/registration", method = RequestMethod.POST)
    public ModelAndView createNewUser(@ModelAttribute("user") @Valid UserEntity user, BindingResult bindingResult) throws Exception {
        ModelAndView modelAndView = new ModelAndView();
        UserEntity userExists = userService.findByLogin(user.getLogin());
        List<String> errors = new ArrayList<>();
        if (userExists != null) {
            errors.add("*This login has already been used");
        }
        if (bindingResult.hasErrors() || !errors.isEmpty()) {
            modelAndView.setViewName(getUser(modelAndView,errors));
        } else {
            userService.saveOrUpdate(user);
            modelAndView.addObject("successMessage", "Регистрация прошла успешно");
            modelAndView.addObject("login", user.getLogin());
            modelAndView.addObject("password", user.getPassword());
            modelAndView.setViewName("redirect:/");
        }
        return modelAndView;
    }
}
