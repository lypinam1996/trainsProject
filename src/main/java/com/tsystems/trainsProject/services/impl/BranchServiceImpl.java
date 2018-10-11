package com.tsystems.trainsProject.services.impl;
import com.tsystems.trainsProject.dao.StationDAO;
import com.tsystems.trainsProject.dao.impl.BranchDAOImpl;
import com.tsystems.trainsProject.dao.impl.StationDAOImpl;
import com.tsystems.trainsProject.dao.impl.UserDAOImpl;
import com.tsystems.trainsProject.models.BranchLineEntity;
import com.tsystems.trainsProject.models.DetailedInfBranchEntity;
import com.tsystems.trainsProject.models.ScheduleEntity;
import com.tsystems.trainsProject.models.StationEntity;
import com.tsystems.trainsProject.services.BranchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;

import java.util.*;

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
    public  String checkEqualitySerialNumbers(BranchLineEntity branch){
        String error =  "";
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
              error="*Several equal serial numbers was entered";
              ok=false;
          }
          else {
              j++;
          }
        }
        return error;
    }

    @Override
    public  String checkEqualityStations(BranchLineEntity branch){
        String error =  "";
        List<StationEntity> listStation = new ArrayList<>();
        List<DetailedInfBranchEntity> detailedInfList = new ArrayList<>();
        detailedInfList.addAll(branch.getDetailedInf());
        for(int i = 0;i<detailedInfList.size();i++){
            listStation.add(detailedInfList.get(i).getStation());
        }
        int j=0;
        boolean ok = true;
        while (j<listStation.size() && ok) {
            if(listStation.indexOf(listStation.get(j))!=listStation.lastIndexOf(listStation.get(j))){
                error="*Several equal stations was selected";
                ok=false;
            }
            else {
                j++;
            }
        }
        return error;
    }

    @Override
    public  String  checkSerialNumbers(BranchLineEntity branch){
        String error = "";
        List<DetailedInfBranchEntity> detailedInfList = new ArrayList<>();
        detailedInfList.addAll(branch.getDetailedInf());
        Map<Integer,DetailedInfBranchEntity> mapSerialNumbers =new TreeMap();
        for(int i = 0;i<detailedInfList.size();i++){
            mapSerialNumbers.put(detailedInfList.get(i).getStationSerialNumber(),detailedInfList.get(i));
        }
        DetailedInfBranchEntity pred = null;
        int diff=0;
        for (Map.Entry<Integer, DetailedInfBranchEntity> entry : mapSerialNumbers.entrySet()) {
            if(pred!=null) {
                diff = entry.getKey()-pred.getStationSerialNumber();
                if(diff>1){
                    entry.getValue().setStationSerialNumber(entry.getKey()-diff+1);
                }
            }
            pred=entry.getValue();
        }
        if(diff>1){
            error="*Some serial numbers was missed";
        }
        return error;
    }

    private  void  check(List<DetailedInfBranchEntity> detailedInfList){
        for(int i = 0;i<detailedInfList.size();i++){
            for(int j = 0;j<detailedInfList.size();j++) {
                if (detailedInfList.get(i).getStationSerialNumber() == detailedInfList.get(j).getStationSerialNumber() && i != j) {
                    detailedInfList.get(j).setStationSerialNumber(detailedInfList.get(j).getStationSerialNumber() + 1);
                }
            }
        }
    }

    @Override
    public  String checkStations(BranchLineEntity branch){
        String error="";
        List<DetailedInfBranchEntity> detailedInfList = branch.getDetailedInf();
        Map<Integer,StationEntity> mapSerialNumbers =new TreeMap();
        for(int i = 0;i<detailedInfList.size();i++){
            mapSerialNumbers.put(detailedInfList.get(i).getStationSerialNumber(),detailedInfList.get(i).getStation());
        }
        BranchLineEntity branchIdeal=findBranchWithMaxEqualStation(branch);
        List<DetailedInfBranchEntity> idealInfList = branchIdeal.getDetailedInf();
        Map<Integer,StationEntity> mapIdealSerialNumbers =new TreeMap();
        for(int i = 0;i<idealInfList.size();i++){
            mapIdealSerialNumbers.put(idealInfList.get(i).getStationSerialNumber(),idealInfList.get(i).getStation());
        }
        int min=0;
        for (Map.Entry<Integer, StationEntity > entry : mapIdealSerialNumbers.entrySet()) {
            for (Map.Entry<Integer, StationEntity > entry2 : mapSerialNumbers.entrySet()) {
                if (entry2.getValue().getStationName().equals(entry.getValue().getStationName())) {
                    for (int j = 0; j < detailedInfList.size(); j++) {
                        if (detailedInfList.get(j).getStation().getStationName().equals(entry.getValue().getStationName())) {
                            min=getMin(mapSerialNumbers.keySet(),min);
                            detailedInfList.get(j).setStationSerialNumber(min);
                        }
                    }
                }

            }
        }
        check(detailedInfList);
        return error;
    }

    private int getMin(Set<Integer> setNumbers,int minimum){
        Integer[] numbers = setNumbers.toArray(new Integer[setNumbers.size()]);
        int j=0;
        boolean ok=true;
        int min=0;
        while (j<numbers.length && ok) {
            if (numbers[j]>minimum){
                min=numbers[j];
                ok=false;
            }
            else{
                j++;
            }
        }
        for(int i=1;i<numbers.length;i++){
            if(numbers[i]<min && numbers[i]>minimum){
                min=numbers[i];
            }
        }
        return min;
    }

    private BranchLineEntity findBranchWithMaxEqualStation(BranchLineEntity branch){
        List<BranchLineEntity> listBranch=branchDAO.findAllBranches();
        Map<BranchLineEntity,List<StationEntity>> mapStationBranch= new HashMap<>();
        List<DetailedInfBranchEntity> list = branch.getDetailedInf();
        for(int i=0;i<listBranch.size();i++){
            List<DetailedInfBranchEntity> detInf=listBranch.get(i).getDetailedInf();
            List<StationEntity> stations = new ArrayList<>();
            for(int j=0;j<detInf.size();j++) {
                for(int x=0;x<list.size();x++) {
                    if (list.get(x).getStation().getStationName().equals(detInf.get(j).getStation().getStationName())) {
                        stations.add(detInf.get(j).getStation());
                    }
                }
            }
            mapStationBranch.put(listBranch.get(i),stations);
        }
        int max=0;
        BranchLineEntity result=new BranchLineEntity();
        for (Map.Entry<BranchLineEntity,List<StationEntity>> entry : mapStationBranch.entrySet()) {
           if(entry.getValue().size()>max){
               max=entry.getValue().size();
               result=entry.getKey();
           }
        }
        return result;
    }

}
