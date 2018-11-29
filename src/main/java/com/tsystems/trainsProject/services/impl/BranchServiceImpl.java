package com.tsystems.trainsProject.services.impl;

import com.tsystems.trainsProject.dao.InfBranchDAO;
import com.tsystems.trainsProject.dao.impl.BranchDAOImpl;
import com.tsystems.trainsProject.dao.impl.StationDAOImpl;
import com.tsystems.trainsProject.models.BranchLineEntity;
import com.tsystems.trainsProject.models.DetailedInfBranchEntity;
import com.tsystems.trainsProject.models.StationEntity;
import com.tsystems.trainsProject.services.BranchService;
import org.apache.commons.lang.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

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
            if (detailedInfList.get(i).getStation().getStationName() != null) {
                branch.getDetailedInf().get(i).setStation(stationDAO.findByName(detailedInfList.get(i).getStation().getStationName()));
            }
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
                    if (detailedInfList.get(i).getStation().getStationName() != null) {
                        branchLineEntity.getDetailedInf().get(j).setStation(stationDAO.findByName(detailedInfList.get(i).getStation().getStationName()));
                    }
                    branchLineEntity.getDetailedInf().get(j).setBranch(branch);
                    if (branch.getDetailedInf().get(i).getStationSerialNumber() != null) {
                        branchLineEntity.getDetailedInf().get(j).setStationSerialNumber(branch.getDetailedInf().get(i).getStationSerialNumber());
                    }
                    if (branch.getDetailedInf().get(i).getTimeFromPrevious() != null) {
                        branchLineEntity.getDetailedInf().get(j).setTimeFromPrevious(branch.getDetailedInf().get(i).getTimeFromPrevious());
                    }
                }
            }
        }
        for (int i = 0; i < detailedInfList.size(); i++) {
            if (infBranchDAO.findById(detailedInfList.get(i).getIdDetailedInfBranch()) == null) {
                if (detailedInfList.get(i).getStation().getStationName() != null) {
                    detailedInfList.get(i).setStation(stationDAO.findByName(detailedInfList.get(i).getStation().getStationName()));
                }
                detailedInfList.get(i).setBranch(branch);
                if (branch.getDetailedInf().get(i).getStationSerialNumber() != null) {
                    detailedInfList.get(i).setStationSerialNumber(branch.getDetailedInf().get(i).getStationSerialNumber());
                }
                if (branch.getDetailedInf().get(i).getTimeFromPrevious() != null) {
                    detailedInfList.get(i).setTimeFromPrevious(branch.getDetailedInf().get(i).getTimeFromPrevious());
                }
                infBranchDAO.saveOrUpdate(detailedInfList.get(i));
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
    public List<String> validation(BranchLineEntity branch) {
        List<String> errors = new ArrayList<>();
        String errorEqualSerialNumber = checkEqualitySerialNumbers(branch);
        String errorNotSerialNumber = checkSerialNumbers(branch);
        String errorEqualStations = checkEqualityStations(branch);
        String errorTime = checkTime(branch);
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

    public BindingResult checkUniqueStationName(StationEntity station, BindingResult bindingResult){
        List<StationEntity> stations = stationDAO.findAllStations();
        stations.remove(stationDAO.findById(station.getIdStation()));
        for (StationEntity stationEntity:stations){
            if(station.getStationName().equals(stationEntity.getStationName())){
                ObjectError objectError = new ObjectError("stationName",
                        "Station name is not unique");
                bindingResult.addError(objectError);
            }
        }
        return bindingResult;
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
        boolean ok = true;
        int i = 0;
        while (i < detailedInfList.size() && ok) {
            if (detailedInfList.get(i).getStationSerialNumber() != null) {
                mapSerialNumbers.put(detailedInfList.get(i).getStationSerialNumber(), detailedInfList.get(i));
                i++;
            } else {
                ok = false;
            }
        }
        if (ok) {
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
        } else {
            error = "*Some serial numbers was missed";
        }
        return error;
    }

    private String checkTime(BranchLineEntity branch) {
        String error = "";
        try {
            if (branch.getDetailedInf().get(0).getTimeFromPrevious() != null) {
                Date timeZero = new SimpleDateFormat("HH:mm").parse("00:00");
                if (!DateUtils.isSameInstant(branch.getDetailedInf().get(0).getTimeFromPrevious(), timeZero)) {
                    error = "*Time at first station must be equal 00:00";
                }
                int i = 1;
                boolean ok = true;
                while (ok && i < branch.getDetailedInf().size()) {
                    if (DateUtils.isSameInstant(branch.getDetailedInf().get(i).getTimeFromPrevious(), timeZero)) {
                        error = "*Time can not be equal 00:00";
                        ok = false;
                    } else {
                        i++;
                    }
                }
            } else {
                error = "*PLease input time";
            }
        }
        catch (ParseException exc){
            error = "*Please verify your input data";
        }
        finally {
            return error;
        }
    }
}
