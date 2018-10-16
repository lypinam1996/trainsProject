package com.tsystems.trainsProject.services.impl;

import com.tsystems.trainsProject.dao.impl.InfBranchDAOImpl;
import com.tsystems.trainsProject.dao.impl.ScheduleDAOImpl;
import com.tsystems.trainsProject.models.*;
import com.tsystems.trainsProject.services.InfBranchService;
import com.tsystems.trainsProject.services.ScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service("ScheduleService")
@Transactional
public class ScheduleServiceImpl implements ScheduleService {

    @Autowired
    ScheduleDAOImpl scheduleDAO;

    @Autowired
    InfBranchDAOImpl infBranchDAO;

    @Autowired
    InfBranchService infBranchService;

    @Override
    public List<ScheduleEntity> findAllSchedules() {
        List<ScheduleEntity> res = scheduleDAO.findAllSchedules();
        return res;
    }

    @Override
    public List<ScheduleEntity> findScheduleByBranch(BranchLineEntity branchLine) {
        return scheduleDAO.findSchedulesByBranch(branchLine);
    }


    public List<ScheduleEntity> findSchedulesByBranch(Map<BranchLineEntity,
            List<DetailedInfBranchEntity>> detailedInfBranchList,
                                                      StationEntity firstStation,
                                                      StationEntity lastStation) {
        List<ScheduleEntity> result = new ArrayList<>();
        for (Map.Entry<BranchLineEntity, List<DetailedInfBranchEntity>> detailedInf : detailedInfBranchList.entrySet()) {
            List<ScheduleEntity> schedules = findScheduleByBranch(detailedInf.getKey());
            for (int i = 0; i < schedules.size(); i++) {
                StationEntity firstStationInSchedule = schedules.get(i).getFirstStation();
                StationEntity lastStationInSchedule = schedules.get(i).getLastStation();
                int numberFirstStationInSchedule = 0;
                int numberFirstStation = 0;
                int numberLastStationInSchedule = 0;
                int numberLastStation = 0;
                for (int j = 0; j < detailedInf.getValue().size(); j++) {
                    if (detailedInf.getValue().get(j).getStation().equals(firstStationInSchedule)) {
                        numberFirstStationInSchedule = detailedInf.getValue().get(j).getStationSerialNumber();
                    }
                    if (detailedInf.getValue().get(j).getStation().equals(firstStation)) {
                        numberFirstStation = detailedInf.getValue().get(j).getStationSerialNumber();
                    }
                    if (detailedInf.getValue().get(j).getStation().equals(lastStationInSchedule)) {
                        numberLastStationInSchedule = detailedInf.getValue().get(j).getStationSerialNumber();
                    }
                    if (detailedInf.getValue().get(j).getStation().equals(lastStation)) {
                        numberLastStation = detailedInf.getValue().get(j).getStationSerialNumber();
                    }
                }
                if (numberFirstStationInSchedule <= numberFirstStation && numberLastStationInSchedule >= numberLastStation) {
                    result.add(schedules.get(i));
                }
            }

        }

        return result;
    }

    @Override
    public void saveOrUpdate(ScheduleEntity schedule) {
        scheduleDAO.saveOrUpdate(schedule);
    }

    public String checkStationsSerialNumbers(ScheduleEntity schedule) {
        String error = "";
        BranchLineEntity branch = schedule.getBranch();
        StationEntity firstStation = schedule.getFirstStation();
        StationEntity lastStation = schedule.getLastStation();
        int numberFistStation = 0;
        int numberLastStation = 0;
        for (int i = 0; i < branch.getDetailedInf().size(); i++) {
            if (branch.getDetailedInf().get(i).getStation().equals(firstStation)) {
                numberFistStation = branch.getDetailedInf().get(i).getStationSerialNumber();
            }
            if (branch.getDetailedInf().get(i).getStation().equals(lastStation)) {
                numberLastStation = branch.getDetailedInf().get(i).getStationSerialNumber();
            }
        }
        if (numberFistStation >= numberLastStation) {
            error = "*Error in entering stations";
        }
        return error;
    }

    public String checkBranchEmployment(ScheduleEntity schedule) {
        String error = "";
        BranchLineEntity branch = schedule.getBranch();
        List<ScheduleEntity> schedulesOnBranch = branch.getSchedule();
        List<Date> employmentTime = evaluateTime(schedulesOnBranch);
        return compareTime(employmentTime,schedule);
    }

    public String checkTrainEmployment(ScheduleEntity schedule){
        String error = "";
        TrainEntity train = schedule.getTrain();
        List<ScheduleEntity> schedulesWithTrain = train.getSchedule();
        List<Date> employmentTime = evaluateTime(schedulesWithTrain);
        return compareTime(employmentTime,schedule);
    }

    private String compareTime(List<Date> employmentTime,ScheduleEntity schedule ){
        String error = "";
        List<ScheduleEntity> schedules = new ArrayList<>();
        schedules.add(schedule);
        List<Date> timeToSave=evaluateTime(schedules);
        int timeToSave1=timeToSave.get(0).getHours()*60+timeToSave.get(0).getMinutes();
        int timeToSave2=timeToSave.get(1).getHours()*60+timeToSave.get(1).getMinutes();
        boolean ok=true;
        int i=0;
        while (i<employmentTime.size() && ok){
            int time1=employmentTime.get(i).getHours()*60+employmentTime.get(i).getMinutes();
            int time2=employmentTime.get(i+1).getHours()*60+employmentTime.get(i+1).getMinutes();
            if(time2<timeToSave1 || time1>timeToSave2){
                i=i+2;
            }
            else{
                error="*Time is busy";
                ok=false;
            }
        }
        return error;
    }

    private List<Date> evaluateTime(List<ScheduleEntity> schedule) {
        List<Date> result = new ArrayList<>();
        try {

            for (int i = 0; i < schedule.size(); i++) {
                int depTime = schedule.get(i).getDepartureTime().getHours() * 60 + schedule.get(i).getDepartureTime().getMinutes();
                List<DetailedInfBranchEntity> detailedInf = infBranchService.findDetailedInformation(schedule.get(i).getBranch());
                int numberFirstStation = 0;
                int numberLastStation = 0;
                for (int j = 0; j < detailedInf.size(); j++) {
                    if (detailedInf.get(j).getStation().equals(schedule.get(i).getFirstStation())) {
                        numberFirstStation = detailedInf.get(j).getStationSerialNumber();
                    }
                    if (detailedInf.get(j).getStation().equals(schedule.get(i).getLastStation())) {
                        numberLastStation = detailedInf.get(j).getStationSerialNumber();
                    }
                }
                Date departureTime = strToTime(intToTime(depTime));
                int arrTime = depTime;
                for (int y = numberFirstStation+1; y <=numberLastStation; y++) {
                    DetailedInfBranchEntity inf = infBranchService.findBySerialNumberStationAndSchedule(y, detailedInf.get(0).getBranch());
                    arrTime = arrTime + inf.getTimeFromPrevious().getMinutes() + inf.getTimeFromPrevious().getHours() * 60;
                }
                Date arrivalTime = strToTime(intToTime(arrTime));
                result.add(departureTime);
                result.add(arrivalTime);
            }
        }
        catch (ParseException e){

        }
        finally {
            return result;
        }
    }

    private Date strToTime(String strTime) throws ParseException {
        DateFormat df = new SimpleDateFormat("hh:mm");
        Date result = df.parse(strTime);
        return result;
    }

    private String intToTime(int intTime) {
        String startTime = "00:00";
        int h = intTime / 60 + Integer.parseInt(startTime.substring(0, 1));
        int m = intTime % 60 + Integer.parseInt(startTime.substring(3, 4));
        String newtime = h + ":" + m;
        return newtime;
    }

    @Override
    public ScheduleEntity findById(int id) {
        return scheduleDAO.findById(id);
    }

    @Override
    public void delete(ScheduleEntity schedule) {
        scheduleDAO.delete(schedule);
    }

    @Override
    public List<String> validation(ScheduleEntity schedule) {
        List<String> errors = new ArrayList<>();
        if(!checkBranchEmployment(schedule).equals("")){
            errors.add(checkBranchEmployment(schedule));
        }
        if(!checkStationsSerialNumbers(schedule).equals("")){
            errors.add(checkStationsSerialNumbers(schedule));
        }
        if(!checkTrainEmployment(schedule).equals("")){
            errors.add(checkTrainEmployment(schedule));
        }
       return errors;
    }


}
