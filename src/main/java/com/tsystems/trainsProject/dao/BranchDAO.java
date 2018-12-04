package com.tsystems.trainsProject.dao;

import com.tsystems.trainsProject.models.BranchLineEntity;

import java.util.List;

public interface BranchDAO {
    List<BranchLineEntity> findAllBranches();

    void saveOrUpdate(BranchLineEntity branch);

    BranchLineEntity findById(int id);

    void update(BranchLineEntity branch);

    void delete(BranchLineEntity branch);
}
