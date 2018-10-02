package com.tsystems.trainsProject.services;

import com.tsystems.trainsProject.models.BranchLineEntity;

import java.util.List;

public interface BranchService {
    List<BranchLineEntity> findAllBranches();
    void saveOrUpdate(BranchLineEntity branch);
    BranchLineEntity findById(int id);
    void update(BranchLineEntity branch);
}
