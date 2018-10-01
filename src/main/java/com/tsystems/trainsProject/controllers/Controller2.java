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
public class Controller2 {

    @Autowired
    BranchService branchService;

    @Autowired
    InfBranchService detailedInf;

    @Autowired
    StationService stationService;

    private List<DetailedInfBranchEntity> manageInfBranches(BranchLineEntity branch) {
        List<DetailedInfBranchEntity> branchestoremove = new ArrayList<DetailedInfBranchEntity>();
        if (branch.getDetailedInf() != null) {
            for (Iterator<DetailedInfBranchEntity> i = branch.getDetailedInf().iterator(); i.hasNext();) {
                DetailedInfBranchEntity information = i.next();
               // if (branch.getRemove() == 1) {
                //    branchestoremove.add(information);
                  //  i.remove();
                //} else {
                    information.setBranch( branchService.findAllBranches().get(branchService.findAllBranches().size()-1));
                    detailedInf.saveOrUpdate(information);
               // }
            }
        }
        return branchestoremove;
    }

    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public String create(@ModelAttribute BranchLineEntity branch, Model model) {
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



}