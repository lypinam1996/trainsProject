package com.tsystems.trainsProject.controllers;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import com.tsystems.trainsProject.DTO.Converter;
import com.tsystems.trainsProject.DTO.ScheduleDTO;
import com.tsystems.trainsProject.DTO.StationDTO;
import com.tsystems.trainsProject.DTO.UserDTO;
import com.tsystems.trainsProject.Events.CustomSpringEvent;
import com.tsystems.trainsProject.models.ScheduleEntity;
import com.tsystems.trainsProject.models.StationEntity;
import com.tsystems.trainsProject.models.UserEntity;
import com.tsystems.trainsProject.services.ScheduleService;
import com.tsystems.trainsProject.services.StationService;
import com.tsystems.trainsProject.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MainRestController {

    @Autowired
    ScheduleService scheduleService;

    @Autowired
    UserService userService;

    @Autowired
    StationService stationService;

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

    @RequestMapping(value = "/restStations", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<StationDTO> restGettingStations()  {
        List<StationDTO> stationsDTO = new ArrayList<StationDTO>();
        List<StationEntity> stations = stationService.findAllStations();
        Converter converter = new Converter();
        for (int i = 0; i < stations.size(); i++) {
            stationsDTO.add(converter.convertStation(stations.get(i)));
        }
        return stationsDTO;
    }

    @RequestMapping(value = "/session/current", produces = MediaType.APPLICATION_JSON_VALUE)
    public UserDTO restGettingUser()  {
        Converter converter = new Converter();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserEntity user = userService.findByLogin(auth.getName());
        UserDTO userDTO = new UserDTO();
        if (user != null) {
            userDTO=converter.convertUser(user);
        }
        return userDTO;
    }

    private List<ScheduleDTO>  helper() throws ParseException {
        List<ScheduleDTO> scheduleDTO = new ArrayList<ScheduleDTO>();
        List<ScheduleEntity> scheduleEntities = scheduleService.findAllSchedulesAfterTime();
        Converter converter = new Converter();
        for (int i = 0; i < scheduleEntities.size(); i++) {
            scheduleDTO.add(converter.convertSchedule(scheduleEntities.get(i)));
        }
        return scheduleDTO;
    }
}
