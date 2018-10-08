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
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.ArrayList;
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

    //переписывает вместо добавления!!!
    private List<DetailedInfBranchEntity> manageInfBranches(BranchLineEntity branch) {
        List<DetailedInfBranchEntity> branchestoremove = new ArrayList<DetailedInfBranchEntity>();
        if (branch.getDetailedInf() != null) {
            for (Iterator<DetailedInfBranchEntity> i = branch.getDetailedInf().iterator(); i.hasNext();) {
                DetailedInfBranchEntity information = i.next();
                information.setBranch( branchService.findAllBranches().get(branchService.findAllBranches().size()-1));
                if ( detailedInf.saveOrUpdate(information) ==0) {
                    branchestoremove.add(information);
                }
            }
        }
        return branchestoremove;
    }

    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public String create(@ModelAttribute BranchLineEntity branch, Model model)
    {

        return create(branch, model, true);
    }

    private String create(BranchLineEntity branch, Model model, boolean init) {
        if (init) {
            branch.setDetailedInf(new AutoPopulatingList<DetailedInfBranchEntity>(DetailedInfBranchEntity.class));
        }

        model.addAttribute("branch",branch);
        model.addAttribute("type", "create");
        return "edit";
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public String create(@ModelAttribute BranchLineEntity branch, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return create(branch, model, false);
        }
        branchService.saveOrUpdate(branch);
        manageInfBranches(branch);
        return "redirect:/trains";
    }

    @RequestMapping(value = "/update/{pk}", method = RequestMethod.GET)
    public String update(@PathVariable Integer pk, Model model) {
        BranchLineEntity branch = branchService.findById(pk);
        List<StationEntity> stations = stationService.findAllStations();
        model.addAttribute("stations",stations);
        model.addAttribute("branch",branch);
        model.addAttribute("type", "update");
        return "edit";
    }


    @RequestMapping(value = "/updateBranch", method = RequestMethod.POST)
    public String update( @ModelAttribute BranchLineEntity branch, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return update(branch.getIdBranchLine(), model);
        }
        List<DetailedInfBranchEntity> employees2remove = manageInfBranches(branch);
        for (DetailedInfBranchEntity detInf : employees2remove) {
           // if (detInf.getIdDetailedInfBranch() != null) {
                detailedInf.delete(detInf);
           // }
        }
        return "redirect:/trains";
    }


    @RequestMapping(value = "/show/{pk}", method = RequestMethod.GET)
    public String show(@PathVariable Integer pk, @ModelAttribute BranchLineEntity employer) {
        // Add your own getEmployerById(pk)
        return "show";
    }



}