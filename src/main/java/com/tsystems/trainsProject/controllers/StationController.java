package com.tsystems.trainsProject.controllers;

import com.tsystems.trainsProject.models.*;
import com.tsystems.trainsProject.services.StationService;
import com.tsystems.trainsProject.services.TrainService;
import com.tsystems.trainsProject.services.impl.SearchTrain;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Controller
public class StationController {

    @Autowired
    StationService stationService;

    @RequestMapping(value = "/stations", method = RequestMethod.GET)
    public ModelAndView getAllStations() {
        ModelAndView model = new ModelAndView();
        List<StationEntity> stations = stationService.findAllStations();
        model.addObject("stations", stations);
        model.setViewName("stations");
        return model;
    }

    @RequestMapping(value = "/createStation", method = RequestMethod.GET)
    public String getStation(@ModelAttribute StationEntity station, Model model)
    {
        return create(station, model, "createStation");
    }

    private String create(StationEntity station, Model model, String type) {
        model.addAttribute("station",station);
        model.addAttribute("type", type);
        return "editStation";
    }

    @RequestMapping(value = "/createStation", method = RequestMethod.POST)
    public String postStation(@ModelAttribute StationEntity station, Model model) {
        stationService.saveOrUpdate(station);
        return "redirect:/stations";
    }

    @RequestMapping(value = "/updateStation/{pk}", method = RequestMethod.GET)
    public String updateStation(@PathVariable Integer pk, Model model) {
        StationEntity station = stationService.findById(pk);
        model.addAttribute("station",station);
        model.addAttribute("type", "updateStation");
        return "editStation" ;
    }


    @RequestMapping(value = "/updateStation", method = RequestMethod.POST)
    public String update( @ModelAttribute StationEntity station, Model model) {
        stationService.saveOrUpdate(station);
        return "redirect:/stations";
    }

    @RequestMapping(value = "/deleteStation/{pk}", method = RequestMethod.GET)
    public String deleteStation(@PathVariable Integer pk, Model model) {
        StationEntity station = stationService.findById(pk);
        stationService.delete(station);
        return "redirect:/stations";
    }

}
