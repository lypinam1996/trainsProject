package com.tsystems.trainsProject.services.impl;

import com.tsystems.trainsProject.dao.BranchDAO;
import com.tsystems.trainsProject.dao.StationDAO;
import com.tsystems.trainsProject.dao.TrainDAO;
import com.tsystems.trainsProject.events.EditingEvent;
import com.tsystems.trainsProject.dao.impl.InfBranchDAOImpl;
import com.tsystems.trainsProject.dao.impl.ScheduleDAOImpl;
import com.tsystems.trainsProject.models.*;
import com.tsystems.trainsProject.services.InfBranchService;
import com.tsystems.trainsProject.services.ScheduleService;
import org.apache.commons.lang.time.DateUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;


import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.*;

@Service("ScheduleService")
@Transactional
public class ScheduleServiceImpl implements ScheduleService {

    private static final Logger logger = Logger.getLogger(BranchServiceImpl.class);

    @Autowired
    ScheduleDAOImpl scheduleDAO;

    @Autowired
    InfBranchDAOImpl infBranchDAO;

    @Autowired
    BranchDAO branchDAO;

    @Autowired
    InfBranchService infBranchService;

    @Autowired
    StationDAO stationDAO;

    @Autowired
    TrainDAO trainDAO;

    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;

    @Override
    public List<ScheduleEntity> findAllSchedules() {
        List<ScheduleEntity> res = scheduleDAO.findAllSchedules();
        return res;
    }

    @Override
    public List<ScheduleEntity> findAllSchedulesAfterTime() {
        List<ScheduleEntity> schedules = scheduleDAO.findAllSchedules();
        List<ScheduleEntity> res = new ArrayList<>();
        Date now = new Date();
        LocalTime today = LocalDateTime.ofInstant(now.toInstant(), ZoneId.systemDefault()).toLocalTime();
        for (int i = 0; i < schedules.size(); i++) {
            LocalTime scheduleTime = LocalDateTime.ofInstant(schedules.get(i).getDepartureTime().toInstant(), ZoneId.systemDefault()).toLocalTime();
            if (scheduleTime.isAfter(today)) {
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
        logger.info("ScheduleServiceImpl: find scheduals by branch");
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
        logger.info("ScheduleServiceImpl: scheduals has been found");
        return result;
    }

    @Override
    public void saveOrUpdate(ScheduleEntity schedule)  {
        int id = 0;
        if (schedule.getIdSchedule() == 0) {
            id = scheduleDAO.save(schedule);
        } else {
            id = schedule.getIdSchedule();
            scheduleDAO.update(schedule);
        }
        Date now = new Date();
        LocalTime today = LocalDateTime.ofInstant(now.toInstant(), ZoneId.systemDefault()).toLocalTime();
        LocalTime scheduleTime = LocalDateTime.ofInstant(schedule.getDepartureTime().toInstant(), ZoneId.systemDefault()).toLocalTime();
        if (today.isBefore(scheduleTime)) {
            logger.info("ScheduleServiceImpl: start to send event");
            EditingEvent customSpringEvent = new EditingEvent(this, String.valueOf(id), 0);
            applicationEventPublisher.publishEvent(customSpringEvent);
            logger.info("ScheduleServiceImpl: event has been sent");
        }
    }

    private String checkStationsSerialNumbers(ScheduleEntity schedule) {
        logger.info("ScheduleServiceImpl: check stations serial numbers");
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
        logger.info("ScheduleServiceImpl: schedule has been checked");
        return error;
    }

    private String checkBranchEmployment(ScheduleEntity schedule) {
        logger.info("ScheduleServiceImpl: check branch employment");
        String error = "";
        BranchLineEntity branch = schedule.getBranch();
        List<ScheduleEntity> schedulesOnBranch = branch.getSchedule();
        ScheduleEntity deleteSchecule = scheduleDAO.findById(schedule.getIdSchedule());
        schedulesOnBranch.remove(deleteSchecule);
        List<Date> employmentTime = evaluateTime(schedulesOnBranch);
        error = compareTime(employmentTime, schedule);
        logger.info("ScheduleServiceImpl: schedule has been checked");
        return error;
    }

    public String checkTrainEmployment(ScheduleEntity schedule) {
        logger.info("ScheduleServiceImpl: check train employment");
        String error = "";
        TrainEntity train = schedule.getTrain();
        List<ScheduleEntity> schedulesWithTrain = train.getSchedule();
        ScheduleEntity deleteSchecule = scheduleDAO.findById(schedule.getIdSchedule());
        schedulesWithTrain.remove(deleteSchecule);
        List<Date> employmentTime = evaluateTime(schedulesWithTrain);
        error = compareTime(employmentTime, schedule);
        logger.info("ScheduleServiceImpl: schedule has been checked");
        return error;
    }

    private String compareTime(List<Date> employmentTime, ScheduleEntity schedule) {
        logger.info("ScheduleServiceImpl: start to compare time");
        String error = "";
        List<ScheduleEntity> schedules = new ArrayList<>();
        schedules.add(schedule);
        List<Date> timeToSave = evaluateTime(schedules);
        LocalTime timeToSave1 = LocalDateTime.ofInstant(timeToSave.get(0).toInstant(), ZoneId.systemDefault()).toLocalTime();
        LocalTime timeToSave2 = LocalDateTime.ofInstant(timeToSave.get(1).toInstant(), ZoneId.systemDefault()).toLocalTime();
        boolean ok = true;
        int i = 0;
        while (i < employmentTime.size() && ok) {
            LocalTime emplTime1 = LocalDateTime.ofInstant(employmentTime.get(i + 1).toInstant(), ZoneId.systemDefault()).toLocalTime();
            LocalTime emplTime2 = LocalDateTime.ofInstant(employmentTime.get(i).toInstant(), ZoneId.systemDefault()).toLocalTime();
            if (emplTime1.isBefore(timeToSave1) || emplTime2.isAfter(timeToSave2)) {
                i = i + 2;
            } else {
                error = "*Time is busy";
                ok = false;
            }
        }
        return error;
    }

    private List<Date> evaluateTime(List<ScheduleEntity> schedule) {
        logger.info("ScheduleServiceImpl: start to evaluate time");
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
        int id = scheduleDAO.deleteId(schedule);
        logger.info("ScheduleServiceImpl: start to send event");
        EditingEvent customSpringEvent = new EditingEvent(this, String.valueOf(id), 1);
        applicationEventPublisher.publishEvent(customSpringEvent);
        logger.info("ScheduleServiceImpl: event has been sent");
    }

    @Override
    public BindingResult validation(ScheduleEntity schedule, BindingResult bindingResult) {
        List<String> errors = new ArrayList<>();
        if (schedule.getDepartureTime() == null) {
            ObjectError objectError = new ObjectError("departureTime",
                    "*Please input time.");
            bindingResult.addError(objectError);
        }
        else {
            if (!checkBranchEmployment(schedule).equals("")) {
                ObjectError objectError = new ObjectError("branch",
                        checkBranchEmployment(schedule) + " for branch");
                bindingResult.addError(objectError);
            }
            if (!checkStationsSerialNumbers(schedule).equals("")) {
                ObjectError objectError = new ObjectError("train",
                        checkStationsSerialNumbers(schedule));
                bindingResult.addError(objectError);
            }
            if (!checkTrainEmployment(schedule).equals("")) {
                ObjectError objectError = new ObjectError("firstStation",
                        checkTrainEmployment(schedule) + " for train");
                bindingResult.addError(objectError);
            }
        }
        return bindingResult;
    }

    @Override
    public ScheduleEntity checkNull(ScheduleEntity schedule){
        if (schedule.getBranch().getIdBranchLine() != 0) {
            schedule.setBranch(branchDAO.findById(schedule.getBranch().getIdBranchLine()));
        }
        if (stationDAO.findById(schedule.getFirstStation().getIdStation()) != null) {
            schedule.setFirstStation(stationDAO.findById(schedule.getFirstStation().getIdStation()));
        }
        if (schedule.getLastStation().getIdStation() != 0) {
            schedule.setLastStation(stationDAO.findById(schedule.getLastStation().getIdStation()));
        }
        if (schedule.getTrain().getIdTrain() != 0) {
            schedule.setTrain(trainDAO.findById(schedule.getTrain().getIdTrain()));
        }
        return schedule;
    }
}
