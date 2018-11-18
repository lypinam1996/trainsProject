package com.tsystems.trainsProject.controllers;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import com.tsystems.trainsProject.DTO.Converter;
import com.tsystems.trainsProject.DTO.ScheduleDTO;
import com.tsystems.trainsProject.models.ScheduleEntity;
import com.tsystems.trainsProject.models.UserEntity;
import com.tsystems.trainsProject.services.ScheduleService;
import com.tsystems.trainsProject.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RestScheduleController {

    @Autowired
    ScheduleService scheduleService;

    @Autowired
    UserService userService;

    @RequestMapping(value = "/greeting", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<ScheduleDTO> greeting() throws ParseException {
        List<ScheduleDTO> scheduleDTO = new ArrayList<ScheduleDTO>();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserEntity user = userService.findByLogin(auth.getName());
        if (user != null) {
            if (!user.getRole().getTitle().equals("WORKER")) {
                scheduleDTO=helper();
            }
        } else {
            scheduleDTO=helper();
        }
        return scheduleDTO;
    }

    private List<ScheduleDTO>  helper() throws ParseException {
        List<ScheduleDTO> scheduleDTO = new ArrayList<ScheduleDTO>();
        List<ScheduleEntity> scheduleEntities = scheduleService.findAllSchedules();
        Converter converter = new Converter();
        for (int i = 0; i < scheduleEntities.size(); i++) {
            scheduleDTO.add(converter.convertSchedule(scheduleEntities.get(i)));
        }
        return scheduleDTO;
    }
}
