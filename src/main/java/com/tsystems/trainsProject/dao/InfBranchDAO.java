package com.tsystems.trainsProject.dao;


import com.tsystems.trainsProject.models.BranchLineEntity;
import com.tsystems.trainsProject.models.DetailedInfBranchEntity;
import com.tsystems.trainsProject.models.StationEntity;

import java.util.List;

public interface InfBranchDAO {
    List<DetailedInfBranchEntity> findAllInfBranch();
    List<DetailedInfBranchEntity> findBranchesByStation(StationEntity stationEntity);
    void saveOrUpdate(DetailedInfBranchEntity branch);
    List<DetailedInfBranchEntity> findDetailedInformation(BranchLineEntity branchLineEntity);
}
