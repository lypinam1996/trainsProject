package com.tsystems.trainsProject.controllers;


import com.tsystems.trainsProject.models.BranchLineEntity;
import com.tsystems.trainsProject.models.ScheduleEntity;
import com.tsystems.trainsProject.models.StationEntity;
import com.tsystems.trainsProject.models.TrainEntity;
import com.tsystems.trainsProject.services.BranchService;
import com.tsystems.trainsProject.services.ScheduleService;
import com.tsystems.trainsProject.services.StationService;
import com.tsystems.trainsProject.services.TrainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
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

    @RequestMapping(value = "/schedule", method = RequestMethod.GET)
    public ModelAndView getSchedule() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        ModelAndView modelAndView = new ModelAndView();
        List<ScheduleEntity> schedules = scheduleService.findAllSchedules();
        modelAndView.addObject("auth", auth.getName());
        modelAndView.addObject("schedules", schedules);
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
        schedule.setBranch(branchService.findById(schedule.getBranch().getIdBranchLine()));
        schedule.setFirstStation(stationService.findById(schedule.getFirstStation().getIdStation()));
        schedule.setLastStation(stationService.findById(schedule.getLastStation().getIdStation()));
        schedule.setTrain(trainService.findById(schedule.getTrain().getIdTrain()));
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
        schedule.setBranch(scheduleEntity.getBranch());
        schedule.setFirstStation(stationService.findById(schedule.getFirstStation().getIdStation()));
        schedule.setLastStation(stationService.findById(schedule.getLastStation().getIdStation()));
        schedule.setTrain(trainService.findById(schedule.getTrain().getIdTrain()));
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
