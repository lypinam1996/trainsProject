package com.tsystems.trainsProject.controllers;

import com.tsystems.trainsProject.models.BranchLineEntity;
import com.tsystems.trainsProject.models.DetailedInfBranchEntity;
import com.tsystems.trainsProject.models.StationEntity;
import com.tsystems.trainsProject.services.BranchService;
import com.tsystems.trainsProject.services.InfBranchService;
import com.tsystems.trainsProject.services.StationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.AutoPopulatingList;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

@Controller
public class BranchController {

    @Autowired
    BranchService branchService;

    @Autowired
    InfBranchService detailedInf;

    @Autowired
    StationService stationService;

    @RequestMapping(value = "/branches", method = RequestMethod.GET)
    public String getAllBranches( Model model)
    {
        List<BranchLineEntity> branches = branchService.findAllBranches();
        model.addAttribute("branches",branches);
        return "branches";
    }

    @RequestMapping(value = "/detailedInf/{pk}", method = RequestMethod.GET)
    public String getDetailedInf(@PathVariable Integer pk, Model model) {
        List<DetailedInfBranchEntity> detailedInformation = detailedInf.findDetailedInformation(branchService.findById(pk));
        model.addAttribute("detailedInf",detailedInformation);
        return "detailedInf";
    }

    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public String create(@ModelAttribute BranchLineEntity branch, Model model)
    {
        List<String> errors = new ArrayList<>();
        return create(branch, model, true,errors);
    }

    private String create(BranchLineEntity branch, Model model, boolean init, List<String> errors) {
        if (init) {
            branch.setDetailedInf(new AutoPopulatingList<DetailedInfBranchEntity>(DetailedInfBranchEntity.class));
        }
        List<StationEntity> stations = stationService.findAllStations();
        model.addAttribute("errors",errors);
        model.addAttribute("stations",stations);
        model.addAttribute("branch",branch);
        model.addAttribute("type", "create");
        return "edit";
    }

    //error info business exception try catch
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public String create(@ModelAttribute BranchLineEntity branch, Model model,BindingResult bindingResult) {
        branchService.checkTheNecessityOfSaving(branch);
        List<String> errors=branchService.validation(branch);
        if (!errors.isEmpty()){
            return create(branch, model, false,errors);
        }
        branchService.saveOrUpdate(branch);
        return "redirect:/branches";
    }

    @RequestMapping(value = "/update/{pk}", method = RequestMethod.GET)
    public String update(@PathVariable Integer pk, Model model) {
        List<String> errors = new ArrayList<>();
        BranchLineEntity branch = branchService.findById(pk);
        List<StationEntity> stations = stationService.findAllStations();
        model.addAttribute("errors",errors);
        model.addAttribute("stations",stations);
        model.addAttribute("branch",branch);
        model.addAttribute("type", "update");
        return "edit" ;
    }


    @RequestMapping(value = "/updateBranch", method = RequestMethod.POST)
    public String update( @ModelAttribute BranchLineEntity branch, BindingResult bindingResult, Model model) {
        branchService.checkTheNecessityOfSaving(branch);
        detailedInf.delete(branch,branch.getIdBranchLine());
        List<String> errors=branchService.validation(branch);
        if (!errors.isEmpty()){
            return create(branch, model,false,errors);
        }
        branchService.saveOrUpdate(branch);
        return "redirect:/branches";
    }
}