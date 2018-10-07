package com.tsystems.trainsProject.controllers;


import com.tsystems.trainsProject.models.ScheduleEntity;
import com.tsystems.trainsProject.services.ScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class ScheduleController {

    @Autowired
    ScheduleService scheduleService;

    @RequestMapping(value = "/schedule", method = RequestMethod.GET)
    public ModelAndView getSchedule() {
        ModelAndView modelAndView = new ModelAndView();
        List<ScheduleEntity> schedules=scheduleService.findAllSchedules();
        modelAndView.addObject("schedules",schedules);
        modelAndView.setViewName("schedule");
        return modelAndView;
    }
}
