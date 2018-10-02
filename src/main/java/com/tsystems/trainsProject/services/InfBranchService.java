package com.tsystems.trainsProject.services;


import com.tsystems.trainsProject.models.BranchLineEntity;
import com.tsystems.trainsProject.models.DetailedInfBranchEntity;
import com.tsystems.trainsProject.models.StationEntity;

import java.util.List;
import java.util.Map;

public interface InfBranchService {
    List<DetailedInfBranchEntity> findAllInfBranch();
    List<DetailedInfBranchEntity> findBranchesByStation(StationEntity stationEntity);
    List<DetailedInfBranchEntity> findDetailedInformation(BranchLineEntity branchLineEntity);
    int saveOrUpdate(DetailedInfBranchEntity branch);
    void delete(DetailedInfBranchEntity branch);
    Map<BranchLineEntity,List<DetailedInfBranchEntity>> findBranchesByBranches(List<BranchLineEntity> branchLines);
}
