package com.tsystems.trainsProject.controllers;

import com.tsystems.trainsProject.models.*;
import com.tsystems.trainsProject.services.StationService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import java.util.ArrayList;
import java.util.List;

@Controller
public class StationController {

    @Autowired
    StationService stationService;

    private static final Logger logger = Logger.getLogger(StationController.class);

    @RequestMapping(value = "/createStation", method = RequestMethod.GET)
    public String getStation(@ModelAttribute StationEntity station, Model model)
    {
        String error="";
        return create(station, model, error,"createStation");
    }

    private String create(StationEntity station, Model model,  String error,String type) {
        model.addAttribute("station",station);
        model.addAttribute("type", type);
        model.addAttribute("error", error);
        return "editStation";
    }

    @RequestMapping(value = "/createStation", method = RequestMethod.POST)
    public String postStation(@ModelAttribute StationEntity station, Model model) {
        String error=stationService.checkUniqueStationName(station);
        if (!error.equals("")){
            return create(station, model,error,"createStation");
        }
        stationService.saveOrUpdate(station);
        return "redirect:/stations";
    }

    @RequestMapping(value = "/updateStation/{pk}", method = RequestMethod.GET)
    public ModelAndView updateStation(@PathVariable Integer pk) {
        ModelAndView model = new ModelAndView();
        List<String> errors = new ArrayList<>();
        StationEntity station = stationService.findById(pk);
        if(!station.getDetailedInf().isEmpty()) {
            errors.add("*You can't delete station! Several branches go through it!");
            return getAllStationsHelp(errors);
        }
        else {
            String error = "";
            model.addObject("station", station);
            model.addObject("error", error);
            model.addObject("type", "updateStation");
            model.setViewName("editStation");
            return model;
        }
    }


    @RequestMapping(value = "/updateStation", method = RequestMethod.POST)
    public String update( @ModelAttribute StationEntity station, Model model) {
        String error=stationService.checkUniqueStationName(station);
        if (!error.equals("")){
            return create(station, model,error,"createStation");
        }
        stationService.saveOrUpdate(station);
        return "redirect:/stations";
    }

    @RequestMapping(value = "/deleteStation/{pk}", method = RequestMethod.GET)
    public ModelAndView deleteStation(@PathVariable Integer pk) {
        StationEntity station = stationService.findById(pk);
        ModelAndView model = new ModelAndView();
        List<String> errors = new ArrayList<>();
        if(!station.getDetailedInf().isEmpty()) {
            errors.add("*You can't delete station! Several branches go through it!");
            model=getAllStationsHelp(errors);
        }
        else {
            stationService.delete(station);
            model=getAllStationsHelp(errors);
        }
        return model;
    }

    @RequestMapping(value = "/stations", method = RequestMethod.GET)
    public ModelAndView getAllStations() {
        List<String> errors=new ArrayList<>();
        ModelAndView model=getAllStationsHelp(errors);
        return model;
    }

    private ModelAndView getAllStationsHelp(List<String> errors){
        ModelAndView model = new ModelAndView();
        List<StationEntity> stations = stationService.findAllStations();
        model.addObject("stations", stations);
        model.addObject("errors", errors);
        model.setViewName("stations");
        return model;
    }

}
