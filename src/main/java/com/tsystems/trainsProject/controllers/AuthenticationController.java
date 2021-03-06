package com.tsystems.trainsProject.controllers;

import com.tsystems.trainsProject.models.UserEntity;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Controller
public class AuthenticationController {

    private static final Logger logger = Logger.getLogger(AuthenticationController.class);

//    @Autowired
//    UserService userService;

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public ModelAndView login() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("login");
        logger.info("AuthenticationController: return login page");
        return modelAndView;
    }


    @RequestMapping(value = "/registration", method = RequestMethod.GET)
    public ModelAndView registration() {
        ModelAndView modelAndView = new ModelAndView();
        List<String> errors = new ArrayList<>();
        modelAndView.setViewName(getUser(modelAndView, errors));
        logger.info("AuthenticationController: return registration page");
        return modelAndView;
    }

    private String getUser(ModelAndView modelAndView, List<String> errors) {
        UserEntity user = new UserEntity();
        modelAndView.addObject("errors", errors);
        modelAndView.addObject("user", user);
        return "registration";
    }

    @RequestMapping(value = "/registration", method = RequestMethod.POST)
    public ModelAndView createNewUser(@ModelAttribute("user") @Valid UserEntity user,
                                      BindingResult bindingResult) {
        logger.info("AuthenticationController: start registration");
        ModelAndView modelAndView = new ModelAndView();
      //  UserEntity userExists = userService.findByLogin(user.getLogin());
//        if (userExists != null) {
//            ObjectError objectError = new ObjectError("login",
//                    "This login has already been used");
//            bindingResult.addError(objectError);
//        }
        if(user.getLogin()==""){
            ObjectError objectError = new ObjectError("login",
                    "Input login");
            bindingResult.addError(objectError);
        }
        if(user.getPassword()==""){
            ObjectError objectError = new ObjectError("login",
                    "Input password");
            bindingResult.addError(objectError);
        }
        if(user.getPassword().length()<6){
            ObjectError objectError = new ObjectError("login",
                    "Password must be at least six characters");
            bindingResult.addError(objectError);
        }
        if (bindingResult.hasErrors()) {
            modelAndView.setViewName("registration");
        } else {
            //userService.saveOrUpdate(user);
            modelAndView.setViewName("redirect:/");
        }
        logger.info("AuthenticationController: registration has been done");
        return modelAndView;
    }
}
