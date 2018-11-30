package com.tsystems.trainsProject.controllers;

import com.tsystems.trainsProject.models.*;
import com.tsystems.trainsProject.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.Date;
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

    @RequestMapping(value = "/trackIndicator", method = RequestMethod.GET)
    public String getTrackIndicator(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        model.addAttribute("auth", auth.getName());
        return "trackIndicator";
    }

    @RequestMapping(value = "/schedule", method = RequestMethod.GET)
    public ModelAndView getSchedule() {
        List<String> errors = new ArrayList<>();
        ModelAndView modelAndView = new ModelAndView();
        modelAndView=getScheduleHelp(errors);
        return modelAndView;
    }

    public ModelAndView getScheduleHelp(List<String> errors) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        RoleEntity role = new RoleEntity();
        if (!auth.getName().equals("anonymousUser")) {
            UserEntity user = userService.findByLogin(auth.getName());
            role = user.getRole();
        }
        ModelAndView modelAndView = new ModelAndView();
        List<ScheduleEntity> schedules = scheduleService.findAllSchedules();
        List<StationEntity> stations = stationService.findAllStations();
        modelAndView.addObject("stations", stations);
        StationEntity station = new StationEntity();
        modelAndView.addObject("station", station);
        modelAndView.addObject("errors", errors);
        modelAndView.addObject("role", role);
        modelAndView.addObject("schedules", schedules);
        modelAndView.setViewName("schedule");
        return modelAndView;
    }

    @RequestMapping(value = "/createSchedule", method = RequestMethod.GET)
    public String chooseBranch(Model model) {
        List<BranchLineEntity> branches = branchService.findAllBranches();
        model.addAttribute("branches", branches);
        return "chooseBranch";
    }

    @RequestMapping(value = "/createSchedule/{pk}", method = RequestMethod.GET)
    public String getSchedule(@PathVariable Integer pk, @ModelAttribute ScheduleEntity schedule, Model model) {
        BranchLineEntity branchLineEntity = branchService.findById(pk);
        schedule.setBranch(branchLineEntity);
        return create(schedule, model, "createSchedule", branchLineEntity);
    }

    private String create(ScheduleEntity schedule, Model model, String type, BranchLineEntity branchLineEntity) {
        List<TrainEntity> trains = trainService.findAllTrains();
        List<StationEntity> stations = new ArrayList<>();
        for (int i = 0; i < branchLineEntity.getDetailedInf().size(); i++) {
            stations.add(branchLineEntity.getDetailedInf().get(i).getStation());
        }
        model.addAttribute("stations", stations);
        model.addAttribute("type", type);
        model.addAttribute("trains", trains);
        model.addAttribute("schedule", schedule);
        return "editSchedule";
    }

    @RequestMapping(value = "/createSchedule", method = RequestMethod.POST)
    public String createSchedule(@ModelAttribute("schedule") @Validated ScheduleEntity schedule,
                                 Model model, BindingResult bindingResult) {
        schedule=scheduleService.checkNull(schedule);
        bindingResult = scheduleService.validation(schedule,bindingResult);
        if (bindingResult.hasErrors()) {
            return create(schedule, model, "createTrain", schedule.getBranch());
        }
        scheduleService.saveOrUpdate(schedule);
        return "redirect:/schedule";
    }


    @RequestMapping(value = "/prohibitPurchase/{pk}", method = RequestMethod.GET)
    public String prohibitPurchase(@PathVariable Integer pk) {
        ScheduleEntity schedule=scheduleService.findById(pk);
        schedule.setProhibitPurchase(new Date());
        scheduleService.saveOrUpdate(schedule);
        return "redirect:/schedule";
    }

    @RequestMapping(value = "/openPurchase/{pk}", method = RequestMethod.GET)
    public String openPurchase(@PathVariable Integer pk) {
        ScheduleEntity schedule=scheduleService.findById(pk);
        schedule.setProhibitPurchase(null);
        scheduleService.saveOrUpdate(schedule);
        return "redirect:/schedule";
    }

    @RequestMapping(value = "/updateSchedule/{pk}", method = RequestMethod.GET)
    public ModelAndView updateTrain(@PathVariable Integer pk) {
        ModelAndView modelAndView = new ModelAndView();
        ScheduleEntity schedule = scheduleService.findById(pk);
        if(!schedule.getTicket().isEmpty()) {
            List<String> errors = new ArrayList<>();
            errors.add("*You can't update schedule!");
            return getScheduleHelp(errors);
        }
        List<TrainEntity> trains = trainService.findAllTrains();
        List<StationEntity> stations = new ArrayList<>();
        for (int i = 0; i < schedule.getBranch().getDetailedInf().size(); i++) {
            stations.add(schedule.getBranch().getDetailedInf().get(i).getStation());
        }
        modelAndView.addObject("stations", stations);
        modelAndView.addObject("type", "updateSchedule");
        modelAndView.addObject("trains", trains);
        modelAndView.addObject("schedule", schedule);
        modelAndView.setViewName("editSchedule");
        return modelAndView;
    }


    @RequestMapping(value = "/updateSchedule", method = RequestMethod.POST)
    public String update(@ModelAttribute("schedule") @Validated ScheduleEntity schedule,
                         Model model, BindingResult bindingResult)  {
        if (schedule.getBranch().getIdBranchLine() != 0) {
            schedule.setBranch(branchService.findById(schedule.getBranch().getIdBranchLine()));
        }
        schedule=scheduleService.checkNull(schedule);
        bindingResult = scheduleService.validation(schedule,bindingResult);
        if (bindingResult.hasErrors()) {
            return create(schedule, model, "updateSchedule", schedule.getBranch());
        }
        scheduleService.saveOrUpdate(schedule);
        return "redirect:/schedule";
    }

    @RequestMapping(value = "/deleteSchedule/{pk}", method = RequestMethod.GET)
    public ModelAndView deleteSchedule(@PathVariable Integer pk) {
        ModelAndView m = new ModelAndView();
        ScheduleEntity schedule = scheduleService.findById(pk);
        if(!schedule.getTicket().isEmpty()) {
            List<String> errors = new ArrayList<>();
            errors.add("*You can't delete schedule!");
            return getScheduleHelp(errors);
        }
        scheduleService.delete(schedule);
        m.setViewName("redirect:/schedule");
        return m;
    }
}
