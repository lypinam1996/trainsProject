package com.tsystems.trainsProject.services;

import com.tsystems.trainsProject.models.BranchLineEntity;
import com.tsystems.trainsProject.models.DetailedInfBranchEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;

import java.util.List;

public interface BranchService {
    List<BranchLineEntity> findAllBranches();
    void saveOrUpdate(BranchLineEntity branch);
    BranchLineEntity findById(int id);
    void update(BranchLineEntity branch);
    List<DetailedInfBranchEntity> checkTheNecessityOfSaving(BranchLineEntity branch);
    List<String> validation(BranchLineEntity branch);
    void delete(BranchLineEntity branch);
}
