package com.tsystems.trainsProject.services.impl;
import com.tsystems.trainsProject.dao.BranchDAO;
import com.tsystems.trainsProject.dao.InfBranchDAO;
import com.tsystems.trainsProject.dao.StationDAO;
import com.tsystems.trainsProject.dao.impl.BranchDAOImpl;
import com.tsystems.trainsProject.dao.impl.InfBranchDAOImpl;
import com.tsystems.trainsProject.dao.impl.StationDAOImpl;
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
    InfBranchDAO infBranchDAO;

    @Autowired
    StationDAO stationDAO;

    @Autowired
    BranchDAO branchDAO;

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
    public void saveOrUpdate(DetailedInfBranchEntity branch) {
        infBranchDAO.saveOrUpdate(branch);
    }

    @Override
    public void delete(BranchLineEntity branch, int id) {
        BranchLineEntity branchLineEntity = branchDAO.findById(id);
        List<DetailedInfBranchEntity> idealDEtInf = branchLineEntity.getDetailedInf();
        List<DetailedInfBranchEntity> detInf = branch.getDetailedInf();
        List<Integer> listIdealId= new ArrayList<>();
        for(int i =0; i<idealDEtInf.size();i++){
            listIdealId.add(idealDEtInf.get(i).getIdDetailedInfBranch());
        }
        List<Integer> listId= new ArrayList<>();
        for(int i =0; i<detInf.size();i++){
            listId.add(detInf.get(i).getIdDetailedInfBranch());
        }
        for(int i =0; i<idealDEtInf.size();i++) {
            if(!listId.contains(listIdealId.get(i))){
                infBranchDAO.delete(infBranchDAO.findById(listIdealId.get(i)));
            }
        }

    }

    @Override
    public DetailedInfBranchEntity findBySerialNumberStationAndSchedule(int serialNumber, BranchLineEntity branch) {
        return infBranchDAO.findBySerialNumberStationAndSchedule(serialNumber, branch);
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
