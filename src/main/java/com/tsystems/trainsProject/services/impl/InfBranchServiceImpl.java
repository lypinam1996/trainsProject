package com.tsystems.trainsProject.services.impl;
import com.tsystems.trainsProject.dao.InfBranchDAO;
import com.tsystems.trainsProject.dao.impl.InfBranchDAOImpl;
import com.tsystems.trainsProject.models.BranchLineEntity;
import com.tsystems.trainsProject.models.DetailedInfBranchEntity;
import com.tsystems.trainsProject.models.StationEntity;
import com.tsystems.trainsProject.services.InfBranchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("InfBranchService")
@Transactional
public class InfBranchServiceImpl implements InfBranchService {

    @Autowired
    InfBranchDAOImpl infBranchDAO;

    @Override
    public List<DetailedInfBranchEntity> findAllInfBranch() {
        List<DetailedInfBranchEntity> res =infBranchDAO.findAllInfBranch();
        return res;
    }

    @Override
    public List<DetailedInfBranchEntity> findBranchesByStation(StationEntity stationEntity) {
        return infBranchDAO.findBranchesByStation(stationEntity);
    }

    @Override
    public List<DetailedInfBranchEntity> findDetailedInformation(BranchLineEntity branchLineEntity) {
        return infBranchDAO.findDetailedInformation(branchLineEntity);
    }

    @Override
    public Map<BranchLineEntity,List<DetailedInfBranchEntity>> findBranchesByBranches(List<BranchLineEntity> branchLines) {
        Map<BranchLineEntity,List<DetailedInfBranchEntity>> result = new HashMap<>();
        for(int i =0; i<branchLines.size();i++){
            if(findDetailedInformation(branchLines.get(i))!=null){
                result.put(branchLines.get(i),findDetailedInformation(branchLines.get(i)) );
            }
        }
        return result;
    }


}
