package com.tsystems.trainsProject.services.impl;
import com.tsystems.trainsProject.dao.StationDAO;
import com.tsystems.trainsProject.dao.impl.BranchDAOImpl;
import com.tsystems.trainsProject.dao.impl.StationDAOImpl;
import com.tsystems.trainsProject.dao.impl.UserDAOImpl;
import com.tsystems.trainsProject.models.BranchLineEntity;
import com.tsystems.trainsProject.models.DetailedInfBranchEntity;
import com.tsystems.trainsProject.services.BranchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("BranchService")
@Transactional
public class BranchServiceImpl implements BranchService {

    @Autowired
    BranchDAOImpl branchDAO;

    @Autowired
    StationDAOImpl stationDAO;

    @Override
    public List<BranchLineEntity> findAllBranches() {
        List<BranchLineEntity> res =branchDAO.findAllBranches();
        return res;
    }

    @Override
    public void saveOrUpdate(BranchLineEntity branch) {
        List<DetailedInfBranchEntity> detailedInfList =branch.getDetailedInf();
        for(int i=0;i<detailedInfList.size();i++){
            detailedInfList.get(i).setStation(stationDAO.findByName(detailedInfList.get(i).getStation().getStationName()));
            detailedInfList.get(i).setBranch(branch);
        }
        branch.setDetailedInf(detailedInfList);

        branchDAO.saveOrUpdate(branch);
    }

    @Override
    public BranchLineEntity findById(int id) {
        return branchDAO.findById(id);
    }

    @Override
    public void update(BranchLineEntity branch) {
        branchDAO.update(branch);
    }


}
