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
                    for(int l = 0;l<detailedInfList.size();l++) {
                        if(detailedInfList.get(l).getStationSerialNumber()>=detailedInfList.get(j).getStationSerialNumber()) {
                            detailedInfList.get(l).setStationSerialNumber(detailedInfList.get(l).getStationSerialNumber() + 1);
                        }
                    }
                    detailedInfList.get(j).setStationSerialNumber(detailedInfList.get(j).getStationSerialNumber() - 1);
                }
            }
        }
        int minIndex=getMinSerialNumber(detailedInfList);
        if(minIndex>1){
            for(int i = 0;i<detailedInfList.size();i++){
                detailedInfList.get(i).setStationSerialNumber(detailedInfList.get(i).getStationSerialNumber()-minIndex+1);
            }
        }
        int index1=getMinIndexSerialNumber(detailedInfList);
        int index2=getMin(detailedInfList,index1);
        for(int i = 0;i<detailedInfList.size()-1;i++) {
            int difference=detailedInfList.get(index2).getStationSerialNumber()-detailedInfList.get(index1).getStationSerialNumber();
            if(difference>1){
                for(int j =0;j<detailedInfList.size();j++){
                    if(detailedInfList.get(j).getStationSerialNumber()>=detailedInfList.get(index2).getStationSerialNumber()) {
                        detailedInfList.get(j).setStationSerialNumber(detailedInfList.get(j).getStationSerialNumber() - difference + 1);
                    }
                }
            }
            int x=index2;
            index1=index2;
            index2=getMin(detailedInfList,index2);
        }
    }

    private int getMin(List<DetailedInfBranchEntity> detailedInfList, int minimum){
        int result=0;
        int min=detailedInfList.get(minimum).getStationSerialNumber();
        List<DetailedInfBranchEntity> detailedInf=new ArrayList<>();
        for(int i = 0;i<detailedInfList.size();i++){
            if(detailedInfList.get(i).getStationSerialNumber()>min){
                detailedInf.add(detailedInfList.get(i));
            }
            else{
                DetailedInfBranchEntity d = new DetailedInfBranchEntity();
                d.setIdDetailedInfBranch(-1);
                detailedInf.add(d);
            }
        }
        int serNum=0;
        int i=0;
        boolean ok=true;
        while (i<detailedInf.size() && ok) {
            if(detailedInf.get(i).getIdDetailedInfBranch()!=-1){
                serNum=detailedInf.get(i).getStationSerialNumber();
                result=i;
                ok=false;
            }
            else {
                i++;
            }
        }
        for(int j = i;j<detailedInf.size();j++){
            if(detailedInf.get(j).getIdDetailedInfBranch()!=-1) {
                if (detailedInf.get(j).getStationSerialNumber() < serNum) {
                    serNum = detailedInf.get(j).getStationSerialNumber();
                    result = j;
                }
            }
        }
        return result;
    }
;
    private int getMinSerialNumber(List<DetailedInfBranchEntity> detailedInfList){
        int minIndex=detailedInfList.get(0).getStationSerialNumber();
        for(int i = 1;i<detailedInfList.size();i++){
            if(detailedInfList.get(i).getStationSerialNumber()<minIndex){
                minIndex=detailedInfList.get(i).getStationSerialNumber();
            }
        }
        return minIndex;
    }

    private int getMinIndexSerialNumber(List<DetailedInfBranchEntity> detailedInfList){
        int min=detailedInfList.get(0).getStationSerialNumber();
        int minIndex=0;
        for(int i = 1;i<detailedInfList.size();i++){
            if(detailedInfList.get(i).getStationSerialNumber()<min){
                min=detailedInfList.get(i).getStationSerialNumber();
                minIndex=i;
            }
        }
        return minIndex;
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
        boolean ok = true;
        for (Map.Entry<Integer, StationEntity > entry : mapIdealSerialNumbers.entrySet()) {
            for (Map.Entry<Integer, StationEntity > entry2 : mapSerialNumbers.entrySet()) {
                if (entry2.getValue().getStationName().equals(entry.getValue().getStationName())) {
                    for (int j = 0; j < detailedInfList.size(); j++) {
                        if (detailedInfList.get(j).getStation().getStationName().equals(entry.getValue().getStationName())) {
                            detailedInfList.get(j).setStationSerialNumber(entry.getKey());
                            ok=false;
                        }
                    }
                }
            }
        }
        check(detailedInfList);
//        if(ok==false){
//
//            error="*Ambiguous entered way";
//        }
        return error;
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

    @Override
    public String checkTime(BranchLineEntity branchLineEntity){
        String error="";
        BranchLineEntity idealBranch=findBranchWithMaxEqualStation(branchLineEntity);
        List<DetailedInfBranchEntity> idealBranchDetInf = idealBranch.getDetailedInf();
        List<DetailedInfBranchEntity> checkingBranch = branchLineEntity.getDetailedInf();
        for(int i=0;i<idealBranchDetInf.size();i++) {
            for (int j = 0; j < checkingBranch.size(); j++) {
                if (idealBranchDetInf.get(i).getStation().getStationName().equals(checkingBranch.get(j).getStation().getStationName())) {
                    for (int x = i; x < idealBranchDetInf.size(); x++) {
                        for (int y = 0; y < checkingBranch.size(); y++) {
                            if (idealBranchDetInf.get(x).getStation().getStationName().equals(checkingBranch.get(y).getStation().getStationName())) {
                                int idealTimeBetweenStations=0;
                                if(idealBranchDetInf.get(x).getStationSerialNumber()-idealBranchDetInf.get(i).getStationSerialNumber()>1){
                                    for(int k=i;k<x;k++){
                                        idealTimeBetweenStations=idealTimeBetweenStations+idealBranchDetInf.get(k).getTimeFromPrevious().getHours()*60+idealBranchDetInf.get(k).getTimeFromPrevious().getMinutes();
                                    }
                                }
                                int timeBetweenStations=0;
                                if(checkingBranch.get(x).getStationSerialNumber()-checkingBranch.get(i).getStationSerialNumber()>1){
                                    for(int k=i;k<x;k++){
                                        timeBetweenStations=timeBetweenStations+checkingBranch.get(k).getTimeFromPrevious().getHours()*60+checkingBranch.get(k).getTimeFromPrevious().getMinutes();
                                    }
                                }
                                if(idealTimeBetweenStations!=timeBetweenStations){
                                    System.out.println("!");
                                }
                            }
                        }
                    }
                }
            }
        }
        return error;
    }

}
