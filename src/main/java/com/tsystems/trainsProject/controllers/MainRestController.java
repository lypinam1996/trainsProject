package com.tsystems.trainsProject.controllers;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import com.tsystems.trainsProject.dto.*;
import com.tsystems.trainsProject.models.ScheduleEntity;
import com.tsystems.trainsProject.models.UserEntity;
import com.tsystems.trainsProject.services.ScheduleService;
import com.tsystems.trainsProject.services.StationService;
import com.tsystems.trainsProject.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
public class MainRestController {

    @Autowired
    ScheduleService scheduleService;

    @Autowired
    UserService userService;

    @Autowired
    StationService stationService;

    @RequestMapping(value = "/getTrackIndicator", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<ScheduleDTO> greeting() {
        List<ScheduleDTO> scheduleDTO= helper();
        return scheduleDTO;
    }

    @RequestMapping(value = "/restStations", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
    @ResponseBody
    public List<StationDTO> restGettingStations(@RequestParam String term) {
        List<StationDTO> stationsDTO = stationService.getStationsByLetters(term);
        return stationsDTO;
    }

    @RequestMapping(value = "/session/current", produces = MediaType.APPLICATION_JSON_VALUE)
    public UserDTO restGettingUser() {
        Converter converter = new Converter();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserEntity user = userService.findByLogin(auth.getName());
        UserDTO userDTO = new UserDTO();
        if (user != null) {
            userDTO = converter.convertUser(user);
        }
        return userDTO;
    }

    private List<ScheduleDTO> helper(){
        List<ScheduleDTO> scheduleDTO = new ArrayList<ScheduleDTO>();
        List<ScheduleEntity> scheduleEntities = scheduleService.findAllSchedulesAfterTime();
        Converter converter = new Converter();
        for (int i = 0; i < scheduleEntities.size(); i++) {
            scheduleDTO.add(converter.convertSchedule(scheduleEntities.get(i)));
        }
        return scheduleDTO;
    }
}
