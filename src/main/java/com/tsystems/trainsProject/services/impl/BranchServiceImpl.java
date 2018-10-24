package com.tsystems.trainsProject.services.impl;

import com.tsystems.trainsProject.dao.BranchDAO;
import com.tsystems.trainsProject.dao.InfBranchDAO;
import com.tsystems.trainsProject.dao.StationDAO;
import com.tsystems.trainsProject.dao.impl.BranchDAOImpl;
import com.tsystems.trainsProject.dao.impl.StationDAOImpl;
import com.tsystems.trainsProject.dao.impl.UserDAOImpl;
import com.tsystems.trainsProject.models.BranchLineEntity;
import com.tsystems.trainsProject.models.DetailedInfBranchEntity;
import com.tsystems.trainsProject.models.ScheduleEntity;
import com.tsystems.trainsProject.models.StationEntity;
import com.tsystems.trainsProject.services.BranchService;
import org.apache.commons.lang.time.DateUtils;
import org.hibernate.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service("BranchService")
@Transactional
public class BranchServiceImpl implements BranchService {

    @Autowired
    BranchDAOImpl branchDAO;

    @Autowired
    InfBranchDAO infBranchDAO;


    @Autowired
    StationDAOImpl stationDAO;

    @Override
    public List<BranchLineEntity> findAllBranches() {
        List<BranchLineEntity> res = branchDAO.findAllBranches();
        return res;
    }

    @Override
    public void saveOrUpdate(BranchLineEntity branch) {
        List<DetailedInfBranchEntity> detailedInfList = branch.getDetailedInf();
        for (int i = 0; i < detailedInfList.size(); i++) {
            branch.getDetailedInf().get(i).setStation(stationDAO.findByName(detailedInfList.get(i).getStation().getStationName()));
            branch.getDetailedInf().get(i).setBranch(branch);
        }
        branchDAO.saveOrUpdate(branch);
    }

    @Override
    public BranchLineEntity findById(int id) {
        return branchDAO.findById(id);
    }

    @Override
    public void update(BranchLineEntity branch) {
        BranchLineEntity branchLineEntity = branchDAO.findById(branch.getIdBranchLine());
        List<DetailedInfBranchEntity> detailedInfList = branch.getDetailedInf();
        for (int i = 0; i < detailedInfList.size(); i++) {
            for (int j = 0; j < branchLineEntity.getDetailedInf().size(); j++) {
                if (branchLineEntity.getDetailedInf().get(j).getIdDetailedInfBranch() == detailedInfList.get(i).getIdDetailedInfBranch()) {
                    branchLineEntity.getDetailedInf().get(j).setStation(stationDAO.findByName(detailedInfList.get(i).getStation().getStationName()));
                    branchLineEntity.getDetailedInf().get(j).setBranch(branch);
                    branchLineEntity.getDetailedInf().get(j).setStationSerialNumber(branch.getDetailedInf().get(i).getStationSerialNumber());
                    branchLineEntity.getDetailedInf().get(j).setTimeFromPrevious(branch.getDetailedInf().get(i).getTimeFromPrevious());
                }
            }
        }
        for (int i = 0; i < detailedInfList.size(); i++) {
            if (infBranchDAO.findById(detailedInfList.get(i).getIdDetailedInfBranch())==null) {
                detailedInfList.get(i).setStation(stationDAO.findByName(detailedInfList.get(i).getStation().getStationName()));
                detailedInfList.get(i).setBranch(branch);
                detailedInfList.get(i).setStationSerialNumber(branch.getDetailedInf().get(i).getStationSerialNumber());
                detailedInfList.get(i).setTimeFromPrevious(branch.getDetailedInf().get(i).getTimeFromPrevious());
                infBranchDAO.saveOrUpdate( detailedInfList.get(i));
            }
        }

        branchDAO.saveOrUpdate(branchLineEntity);
    }

    @Override
    public List<DetailedInfBranchEntity> checkTheNecessityOfSaving(BranchLineEntity branch) {
        List<DetailedInfBranchEntity> infBranchToRemove = new ArrayList<>();
        if (branch.getDetailedInf() != null) {
            for (Iterator<DetailedInfBranchEntity> i = branch.getDetailedInf().iterator(); i.hasNext(); ) {
                DetailedInfBranchEntity information = i.next();
                if (information.getTimeFromPrevious() == null &&
                        information.getStationSerialNumber() == null &&
                        information.getStation() == null) {
                    infBranchToRemove.add(information);
                }
            }
        }
        for (int i = 0; i < infBranchToRemove.size(); i++) {
            branch.getDetailedInf().remove(infBranchToRemove.get(i));
        }
        return infBranchToRemove;
    }

    @Override
    public List<String> validation(BranchLineEntity branch) throws ParseException {
        List<String> errors = new ArrayList<>();
        String errorEqualSerialNumber = checkEqualitySerialNumbers(branch);
        String errorNotSerialNumber = checkSerialNumbers(branch);
        String errorEqualStations = checkEqualityStations(branch);
        String errorTime = checkTime(branch);
        Date timeZero = new SimpleDateFormat("HH:mm").parse("00:00");
        if (!DateUtils.isSameInstant(branch.getDetailedInf().get(0).getTimeFromPrevious(), timeZero)) {
            errors.add("*Time at first station must be equal 00:00");
        }
        int i = 1;
        boolean ok = true;
        while (ok && i < branch.getDetailedInf().size()) {
            if (DateUtils.isSameInstant(branch.getDetailedInf().get(i).getTimeFromPrevious(), timeZero)) {
                errors.add("*Time can not be equal 00:00");
                ok = false;
            } else {
                i++;
            }
        }
        if (!errorEqualSerialNumber.equals("")) {
            errors.add(errorEqualSerialNumber);
        }
        if (!errorNotSerialNumber.equals("")) {
            errors.add(errorNotSerialNumber);
        }
        if (!errorEqualStations.equals("")) {
            errors.add(errorEqualStations);
        }
        if (!errorTime.equals("")) {
            errors.add(errorTime);
        }
        return errors;
    }

    @Override
    public void delete(BranchLineEntity branch) {
        branchDAO.delete(branch);
    }

    private String checkEqualitySerialNumbers(BranchLineEntity branch) {
        String error = "";
        List<Integer> listSN = new ArrayList<>();
        List<DetailedInfBranchEntity> detailedInfList = new ArrayList<>();
        detailedInfList.addAll(branch.getDetailedInf());
        for (int i = 0; i < detailedInfList.size(); i++) {
            listSN.add(detailedInfList.get(i).getStationSerialNumber());
        }
        int j = 0;
        boolean ok = true;
        while (j < listSN.size() && ok) {
            if (listSN.indexOf(listSN.get(j)) != listSN.lastIndexOf(listSN.get(j))) {
                error = "*Several equal serial numbers was entered";
                ok = false;
            } else {
                j++;
            }
        }
        return error;
    }

    private String checkEqualityStations(BranchLineEntity branch) {
        String error = "";
        List<StationEntity> listStation = new ArrayList<>();
        List<DetailedInfBranchEntity> detailedInfList = new ArrayList<>();
        detailedInfList.addAll(branch.getDetailedInf());
        for (int i = 0; i < detailedInfList.size(); i++) {
            listStation.add(detailedInfList.get(i).getStation());
        }
        int j = 0;
        boolean ok = true;
        while (j < listStation.size() && ok) {
            if (listStation.indexOf(listStation.get(j)) != listStation.lastIndexOf(listStation.get(j))) {
                error = "*Several equal stations was selected";
                ok = false;
            } else {
                j++;
            }
        }
        return error;
    }

    private String checkSerialNumbers(BranchLineEntity branch) {
        String error = "";
        List<DetailedInfBranchEntity> detailedInfList = new ArrayList<>();
        detailedInfList.addAll(branch.getDetailedInf());
        Map<Integer, DetailedInfBranchEntity> mapSerialNumbers = new TreeMap();
        for (int i = 0; i < detailedInfList.size(); i++) {
            mapSerialNumbers.put(detailedInfList.get(i).getStationSerialNumber(), detailedInfList.get(i));
        }
        DetailedInfBranchEntity pred = null;
        int diff = 0;
        for (Map.Entry<Integer, DetailedInfBranchEntity> entry : mapSerialNumbers.entrySet()) {
            if (pred != null) {
                diff = entry.getKey() - pred.getStationSerialNumber();
                if (diff > 1) {
                    entry.getValue().setStationSerialNumber(entry.getKey() - diff + 1);
                }
            }
            pred = entry.getValue();
        }
        if (diff > 1) {
            error = "*Some serial numbers was missed";
        }
        return error;
    }

    private String checkTime(BranchLineEntity branch) {
        String error = "";
        List<DetailedInfBranchEntity> listDetInf = new ArrayList<>();
        for (int i = 1; i < listDetInf.size(); i++) {
            if (listDetInf.get(i).getTimeFromPrevious().getHours() * 60 + listDetInf.get(i).getTimeFromPrevious().getMinutes() == 0) {
                error = "*Time can not be zero";
            }
        }
        return error;
    }
}
