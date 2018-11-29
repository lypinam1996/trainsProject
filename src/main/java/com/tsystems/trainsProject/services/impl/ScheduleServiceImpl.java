package com.tsystems.trainsProject.services.impl;

import com.tsystems.trainsProject.events.EditingEvent;
import com.tsystems.trainsProject.dao.impl.InfBranchDAOImpl;
import com.tsystems.trainsProject.dao.impl.ScheduleDAOImpl;
import com.tsystems.trainsProject.models.*;
import com.tsystems.trainsProject.services.InfBranchService;
import com.tsystems.trainsProject.services.ScheduleService;
import org.apache.commons.lang.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
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

    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;

    @Override
    public List<ScheduleEntity> findAllSchedules() {
        List<ScheduleEntity> res = scheduleDAO.findAllSchedules();
        return res;
    }

    @Override
    public List<ScheduleEntity> findAllSchedulesAfterTime() throws ParseException {
        List<ScheduleEntity> schedules = scheduleDAO.findAllSchedules();
        List<ScheduleEntity> res = new ArrayList<>();
        String pattern = "HH:mm";
        DateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        String stringTime = simpleDateFormat.format(new Date());
        Date now = simpleDateFormat.parse(stringTime);
        for(int i=0;i<schedules.size();i++){
            if(schedules.get(i).getDepartureTime().after(now)){
                res.add(schedules.get(i));
            }
        }
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
    public void saveOrUpdate(ScheduleEntity schedule) throws ParseException {
        int id=0;
        if(schedule.getIdSchedule()==0) {
             id = scheduleDAO.save(schedule);
        }
        else {
            id=schedule.getIdSchedule();
            scheduleDAO.update(schedule);
        }
        String pattern = "HH:mm";
        DateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        String stringTime = simpleDateFormat.format(new Date());
        Date now = simpleDateFormat.parse(stringTime);
        if(now.before(schedule.getDepartureTime())) {
            EditingEvent customSpringEvent = new EditingEvent(this, String.valueOf(id), 0);
            applicationEventPublisher.publishEvent(customSpringEvent);
        }
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
        error=compareTime(employmentTime, schedule);
        return error;
    }

    public String checkTrainEmployment(ScheduleEntity schedule) {
        String error = "";
        TrainEntity train = schedule.getTrain();
        List<ScheduleEntity> schedulesWithTrain = train.getSchedule();
        List<Date> employmentTime = evaluateTime(schedulesWithTrain);
        error=compareTime(employmentTime, schedule);
        return error;
    }

    private String compareTime(List<Date> employmentTime, ScheduleEntity schedule) {
        String error = "";
        List<ScheduleEntity> schedules = new ArrayList<>();
        schedules.add(schedule);
        List<Date> timeToSave = evaluateTime(schedules);
        Date timeToSave1 = timeToSave.get(0);
        Date timeToSave2 = timeToSave.get(1);
        boolean ok = true;
        int i = 0;
        while (i < employmentTime.size() && ok) {
            if (employmentTime.get(i + 1).before(timeToSave1) || employmentTime.get(i).after(timeToSave2)) {
                i = i + 2;
            } else {
                error = "*Time is busy";
                ok = false;
            }
        }
        return error;
    }

    private List<Date> evaluateTime(List<ScheduleEntity> schedule) {
        List<Date> result = new ArrayList<>();

        for (int i = 0; i < schedule.size(); i++) {
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
            Date departureTime = schedule.get(i).getDepartureTime();
            Date arrivalTime = departureTime;
            for (int y = numberFirstStation + 1; y <= numberLastStation; y++) {
                DetailedInfBranchEntity inf = infBranchService.findBySerialNumberStationAndSchedule(y, detailedInf.get(0).getBranch());
                arrivalTime = DateUtils.addHours(arrivalTime, inf.getTimeFromPrevious().getHours());
                arrivalTime = DateUtils.addMinutes(arrivalTime, inf.getTimeFromPrevious().getMinutes());
            }
            result.add(departureTime);
            result.add(arrivalTime);
        }

        return result;

    }

    @Override
    public ScheduleEntity findById(int id) {
        return scheduleDAO.findById(id);
    }

    @Override
    public void delete(ScheduleEntity schedule) {
        int id =scheduleDAO.deleteId(schedule);
        EditingEvent customSpringEvent = new EditingEvent(this, String.valueOf(id), 1);
        applicationEventPublisher.publishEvent(customSpringEvent);

    }

    @Override
    public List<String> validation(ScheduleEntity schedule) {
        List<String> errors = new ArrayList<>();
        if (!checkBranchEmployment(schedule).equals("")) {
            errors.add(checkBranchEmployment(schedule)+" for branch");
        }
        if (!checkStationsSerialNumbers(schedule).equals("")) {
            errors.add(checkStationsSerialNumbers(schedule));
        }
        if (!checkTrainEmployment(schedule).equals("")) {
            errors.add(checkTrainEmployment(schedule)+" for train");
        }
        if(schedule.getDepartureTime()==null){
            errors.add("*Please input time.");
        }
        return errors;
    }


}
