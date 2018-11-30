package com.tsystems.trainsProject.controllers;

import com.tsystems.trainsProject.models.BranchLineEntity;
import com.tsystems.trainsProject.models.DetailedInfBranchEntity;
import com.tsystems.trainsProject.models.StationEntity;
import com.tsystems.trainsProject.services.BranchService;
import com.tsystems.trainsProject.services.InfBranchService;
import com.tsystems.trainsProject.services.StationService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.AutoPopulatingList;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

@Controller
public class BranchController {

    private static final Logger logger = Logger.getLogger(BranchController.class);

    @Autowired
    BranchService branchService;

    @Autowired
    InfBranchService detailedInf;

    @Autowired
    StationService stationService;

    @RequestMapping(value = "/detailedInf/{pk}", method = RequestMethod.GET)
    public String getDetailedInf(@PathVariable Integer pk, Model model) {
        List<DetailedInfBranchEntity> detailedInformation = detailedInf.findDetailedInformation(branchService.findById(pk));
        model.addAttribute("detailedInf",detailedInformation);
        logger.info("BranchController: return detailed information page");
        return "detailedInf";
    }

    @RequestMapping(value = "/createBranch", method = RequestMethod.GET)
    public String create(@ModelAttribute BranchLineEntity branch, Model model){
        List<String> errors = new ArrayList<>();
        logger.info("BranchController: return create branch page");
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
        model.addAttribute("type", "createBranch");
        return "editBranch";
    }

    @RequestMapping(value = "/createBranch", method = RequestMethod.POST)
    public String create(@ModelAttribute BranchLineEntity branch, Model model,BindingResult bindingResult) throws ParseException {
        logger.info("BranchController:  start to save branch");
        branchService.checkTheNecessityOfSaving(branch);
        List<String> errors=branchService.validation(branch);
        if (!errors.isEmpty()){
            logger.info("BranchController: there some validation errors in branch");
            return create(branch, model, false,errors);
        }
        branchService.saveOrUpdate(branch);
        logger.info("BranchController: branch has been saved");
        return "redirect:/branches";
    }

    @RequestMapping(value = "/updateBranch/{pk}", method = RequestMethod.GET)
    public  ModelAndView update(@PathVariable Integer pk) {
        List<String> errors = new ArrayList<>();
        ModelAndView model = new ModelAndView();
        BranchLineEntity branch = branchService.findById(pk);
        if(branch.getSchedule().isEmpty()) {
            List<StationEntity> stations = stationService.findAllStations();
            model.addObject("errors", errors);
            model.addObject("stations", stations);
            model.addObject("branch", branch);
            model.addObject("type", "updateBranch");
            model.setViewName("editBranch");
            logger.info("BranchController: return update branch page");
            return model;
        }
        else {
            errors.add("*You can't edit this branch! Several trains go through it");
            logger.info("BranchController: there are some problems in validation branch");
            return getAllBranchesHelp(errors);
        }

    }

    @RequestMapping(value = "/updateBranch", method = RequestMethod.POST)
    public String update( @ModelAttribute BranchLineEntity branch, Model model) {
        logger.info("BranchController:  start to update branch");
        branchService.checkTheNecessityOfSaving(branch);
        detailedInf.delete(branch,branch.getIdBranchLine());
        List<String> errors=branchService.validation(branch);
        if (!errors.isEmpty()){
            logger.info("BranchController: there are some problems in validation branch");
            return create(branch, model,false,errors);
        }
        branchService.update(branch);
        logger.info("BranchController: branch has been updated");
        return "redirect:/branches";
    }

    @RequestMapping(value = "/deleteBranch/{pk}", method = RequestMethod.GET)
    public ModelAndView deleteBranch(@PathVariable Integer pk) {
        logger.info("BranchController:  start to delete branch");
        ModelAndView model = new ModelAndView();
        BranchLineEntity branch = branchService.findById(pk);
        List<String> errors = new ArrayList<>();
        if(branch.getSchedule().isEmpty()){
            branchService.delete(branch);
            model=getAllBranchesHelp(errors);
            logger.info("BranchController: branch has been deleted");
        }
        else {
            logger.info("BranchController: there are some problems in validation branch");
            errors.add("*You can't delete this branch! Several trains go through it");
            model=getAllBranchesHelp(errors);
        }
        return model;
    }

    @RequestMapping(value = "/branches", method = RequestMethod.GET)
    public ModelAndView getAllBranches()
    {
        List<String> errors = new ArrayList<>();
        logger.info("BranchController: return branch page");
        return getAllBranchesHelp(errors);
    }

    private ModelAndView getAllBranchesHelp( List<String> errors){
        ModelAndView model = new ModelAndView();
        List<BranchLineEntity> branches = branchService.findAllBranches();
        model.addObject("errors",errors);
        model.addObject("branches",branches);
        model.setViewName("branches");
        return model;
    }
}