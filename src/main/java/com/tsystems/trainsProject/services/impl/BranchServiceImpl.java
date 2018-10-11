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
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;

import java.util.ArrayList;
import java.util.Iterator;
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

    @Override
    public List<DetailedInfBranchEntity> checkTheNecessityOfSaving(BranchLineEntity branch){
        List<DetailedInfBranchEntity> infBranchToRemove = new ArrayList<>();
        if (branch.getDetailedInf() != null) {
            for (Iterator<DetailedInfBranchEntity> i = branch.getDetailedInf().iterator(); i.hasNext();) {
                DetailedInfBranchEntity information = i.next();
                if(information.getTimeFromPrevious()==null &&
                        information.getStationSerialNumber()==null &&
                        information.getStation()==null) {
                    infBranchToRemove.add(information);
                }
            }
        }
        for(int i=0;i<infBranchToRemove.size();i++){
            branch.getDetailedInf().remove(infBranchToRemove.get(i));
        }
        return infBranchToRemove;
    }

    @Override
    public  List<String> checkSerialNumbers(BranchLineEntity branch){
        List<String> errors =  new ArrayList<>();
        List<Integer> listSN = new ArrayList<>();
        List<DetailedInfBranchEntity> detailedInfList = new ArrayList<>();
        detailedInfList.addAll(branch.getDetailedInf());
        for(int i = 0;i<detailedInfList.size();i++){
            listSN.add(detailedInfList.get(i).getStationSerialNumber());
        }
        int j=0;
        boolean ok = true;
        while (j<listSN.size() && ok) {
          if(listSN.indexOf(listSN.get(j))!=listSN.lastIndexOf(listSN.get(j))){
              errors.add("*Several equal serial numbers was entered");
              ok=false;
          }
          else {
              j++;
          }
        }
        return errors;
    }
}
