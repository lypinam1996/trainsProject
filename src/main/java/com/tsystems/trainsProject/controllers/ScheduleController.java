package com.tsystems.trainsProject.controllers;


import com.tsystems.trainsProject.models.*;
import com.tsystems.trainsProject.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

@Controller
public class ScheduleController {

    @Autowired
    ScheduleService scheduleService;

    @Autowired
    BranchService branchService;

    @Autowired
    TrainService trainService;

    @Autowired
    StationService stationService;

    @Autowired
    UserService userService;

    @RequestMapping(value = "/schedule", method = RequestMethod.GET)
    public ModelAndView getSchedule() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        RoleEntity role = new RoleEntity();
        if(!auth.getName().equals("anonymousUser")) {
            UserEntity user = userService.findByLogin(auth.getName());
            role=user.getRole();
        }
        ModelAndView modelAndView = new ModelAndView();
        List<ScheduleEntity> schedules = scheduleService.findAllSchedules();
        List<StationEntity> stations = stationService.findAllStations();
        modelAndView.addObject("stations",stations);
        StationEntity station = new StationEntity();
        modelAndView.addObject("station",station);
        modelAndView.addObject("role",role);
        modelAndView.addObject("schedules", schedules);
        modelAndView.setViewName("schedule");
        return modelAndView;
    }

    @RequestMapping(value = "/findStation", method = RequestMethod.POST)
    public ModelAndView getScheduleByStation(@ModelAttribute StationEntity stationGet) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        RoleEntity role = new RoleEntity();
        if(!auth.getName().equals("anonymousUser")) {
            UserEntity user = userService.findByLogin(auth.getName());
            role=user.getRole();
        }
        ModelAndView modelAndView = new ModelAndView();
        StationEntity stationEntity = stationService.findById(stationGet.getIdStation());
        List<ScheduleEntity> schedules = stationEntity.getFirstStation();
        schedules.addAll(stationEntity.getLastStation());
        StationEntity station = new StationEntity();
        modelAndView.addObject("station",station);
        modelAndView.addObject("role",role);
        modelAndView.addObject("schedules", schedules);
        List<StationEntity> stations = stationService.findAllStations();
        modelAndView.addObject("stations",stations);
        modelAndView.setViewName("schedule");
        return modelAndView;
    }



    @RequestMapping(value = "/createSchedule", method = RequestMethod.GET)
    public String getBranhes(Model model) {
        List<BranchLineEntity> branches = branchService.findAllBranches();
        model.addAttribute("branches", branches);
        return "chooseBranch";
    }

    @RequestMapping(value = "/createSchedule/{pk}", method = RequestMethod.GET)
    public String getSchedule(@PathVariable Integer pk, @ModelAttribute ScheduleEntity schedule, Model model) {
        List<String>  errors = new ArrayList<>();
        BranchLineEntity branchLineEntity = branchService.findById(pk);
        schedule.setBranch(branchLineEntity);
        return create(schedule, model, errors, "createSchedule", branchLineEntity);
    }

    private String create(ScheduleEntity schedule, Model model, List<String>  errors, String type, BranchLineEntity branchLineEntity) {
        List<TrainEntity> trains = trainService.findAllTrains();
        List<StationEntity> stations = new ArrayList<>();
        for (int i = 0; i < branchLineEntity.getDetailedInf().size(); i++) {
            stations.add(branchLineEntity.getDetailedInf().get(i).getStation());
        }
        model.addAttribute("errors", errors);
        model.addAttribute("stations", stations);
        model.addAttribute("type", type);
        model.addAttribute("trains", trains);
        model.addAttribute("schedule", schedule);
        return "editSchedule";
    }

    @RequestMapping(value = "/createSchedule", method = RequestMethod.POST)
    public String postTrain( @ModelAttribute ScheduleEntity schedule, Model model) {
        if(schedule.getBranch().getIdBranchLine()!=0) {
            schedule.setBranch(branchService.findById(schedule.getBranch().getIdBranchLine()));
        }
        if(schedule.getFirstStation().getIdStation()!=0) {
            schedule.setFirstStation(stationService.findById(schedule.getFirstStation().getIdStation()));
        }
        if(schedule.getLastStation().getIdStation()!=0) {
            schedule.setLastStation(stationService.findById(schedule.getLastStation().getIdStation()));
        }
        if(schedule.getTrain().getIdTrain()!=0) {
            schedule.setTrain(trainService.findById(schedule.getTrain().getIdTrain()));
        }
        List<String>  errors = scheduleService.validation(schedule);
        if (!errors.isEmpty()) {
            return create(schedule, model, errors, "createTrain", schedule.getBranch());
        }
        scheduleService.saveOrUpdate(schedule);
        return "redirect:/schedule";
    }

    @RequestMapping(value = "/updateSchedule/{pk}", method = RequestMethod.GET)
    public String updateTrain(@PathVariable Integer pk, Model model) {
        List<String>  errors = new ArrayList<>();
        ScheduleEntity schedule = scheduleService.findById(pk);
        List<TrainEntity> trains = trainService.findAllTrains();
        List<StationEntity> stations = new ArrayList<>();
        for (int i = 0; i < schedule.getBranch().getDetailedInf().size(); i++) {
            stations.add(schedule.getBranch().getDetailedInf().get(i).getStation());
        }
        model.addAttribute("errors", errors);
        model.addAttribute("stations", stations);
        model.addAttribute("type", "updateSchedule");
        model.addAttribute("trains", trains);
        model.addAttribute("schedule", schedule);
        return "editSchedule";
    }


    @RequestMapping(value = "/updateSchedule", method = RequestMethod.POST)
    public String update(@ModelAttribute ScheduleEntity schedule, Model model) {
        ScheduleEntity scheduleEntity = scheduleService.findById(schedule.getIdSchedule());
        if(schedule.getBranch().getIdBranchLine()!=0) {
            schedule.setBranch(branchService.findById(schedule.getBranch().getIdBranchLine()));
        }
        if(schedule.getFirstStation().getIdStation()!=0) {
            schedule.setFirstStation(stationService.findById(schedule.getFirstStation().getIdStation()));
        }
        if(schedule.getLastStation().getIdStation()!=0) {
            schedule.setLastStation(stationService.findById(schedule.getLastStation().getIdStation()));
        }
        if(schedule.getTrain().getIdTrain()!=0) {
            schedule.setTrain(trainService.findById(schedule.getTrain().getIdTrain()));
        }
        List<String>  errors = scheduleService.validation(schedule);
        if (!errors.isEmpty()) {
              return create(schedule,model,errors,"updateSchedule",schedule.getBranch());
        }
        scheduleService.saveOrUpdate(schedule);
        return "redirect:/schedule";
    }

    @RequestMapping(value = "/deleteSchedule/{pk}", method = RequestMethod.GET)
    public String deleteSchedule(@PathVariable Integer pk, Model model) {
        ScheduleEntity schedule = scheduleService.findById(pk);
        scheduleService.delete(schedule);
        return "redirect:/schedule";
    }
}
