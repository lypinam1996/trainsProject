package com.tsystems.trainsProject.services.impl;

import com.tsystems.trainsProject.models.*;
import com.tsystems.trainsProject.services.InfBranchService;
import com.tsystems.trainsProject.services.ScheduleService;
import com.tsystems.trainsProject.services.StationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;

import java.sql.Time;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
@Transactional
public class SearchTrain {
    @Autowired
    StationService stationService;

    @Autowired
    InfBranchService infBranchService;

    @Autowired
    ScheduleService scheduleService;

    public Map<ScheduleEntity,List<Date>> search(Search search, BindingResult bindingResult)  {
        Map<ScheduleEntity,List<Date>> result = new HashMap<>();
        try {
            StationEntity departureStation = stationService.findByName(search.getFirstStation());
            StationEntity arrivalStation = stationService.findByName(search.getLastStation());
            List<DetailedInfBranchEntity> listDepStations = infBranchService.findBranchesByStation(departureStation);
            List<DetailedInfBranchEntity> listArrStations = infBranchService.findBranchesByStation(arrivalStation);
            List<BranchLineEntity> branches = findCoincidingBranches(listDepStations, listArrStations);
            Map<BranchLineEntity, List<DetailedInfBranchEntity>> detailedInfBranchList = infBranchService.findBranchesByBranches(branches);
            List<ScheduleEntity> schedule = scheduleService.findSchedulesByBranch(detailedInfBranchList, departureStation, arrivalStation);
            SimpleDateFormat ft = new SimpleDateFormat("hh:mm");
            Date time1 = ft.parse(search.getDepartureTimeFrom());
            Date time2 = ft.parse(search.getDepartureTimeTo());
            result = evaluateTime(schedule, time1, time2, departureStation,arrivalStation);
        }
        catch (ParseException e){
            bindingResult.rejectValue("departureTimeFrom","The time was entered incorrectly");
        }
        return result;
    }

    private List<BranchLineEntity> findCoincidingBranches(List<DetailedInfBranchEntity>  listDepStations,
                                                          List<DetailedInfBranchEntity>  listArrStations){
        List<BranchLineEntity> result = new ArrayList<>();
        for(int i=0;i<listDepStations.size();i++){
            for(int j=0;j<listArrStations.size();j++) {
                if (listDepStations.get(i).getBranch().equals(listArrStations.get(j).getBranch())) {
                    result.add(listDepStations.get(i).getBranch());
                }
            }
        }
        return result;
    }

    private Map<ScheduleEntity,List<Date>> evaluateTime(List<ScheduleEntity> schedule,
                                              Date time1, Date time2,
                                              StationEntity firstStation,
                                              StationEntity lastStation) throws ParseException {
        time1.setMinutes(time1.getMinutes()-1);
        time2.setMinutes(time2.getMinutes()+1);
        Map<ScheduleEntity,List<Date>> result = new HashMap<>();
        for(int i=0;i<schedule.size();i++) {
            int depTime = schedule.get(i).getDepartureTime().getHours()*60+schedule.get(i).getDepartureTime().getMinutes();
            List<DetailedInfBranchEntity> detailedInf = infBranchService.findDetailedInformation(schedule.get(i).getBranch());
            int numberFirstStation = 0;
            int numberLastStation = 0;
            Date departureTime = strToTime(intToTime(depTime));
            int arrTime=depTime;
            if(departureTime.before(time2) && departureTime.after(time1)){
                for (int j = 0; j < detailedInf.size(); j++) {
                    if (detailedInf.get(j).getStation().equals(firstStation)) {
                        numberFirstStation = detailedInf.get(j).getStationSerialNumber();
                    }
                    if (detailedInf.get(j).getStation().equals(lastStation)) {
                        numberLastStation = detailedInf.get(j).getStationSerialNumber();
                    }
                }
                for(int y=numberFirstStation+1;y<=numberLastStation;y++){
                    DetailedInfBranchEntity inf = infBranchService.findBySerialNumberStationAndSchedule(y,detailedInf.get(0).getBranch());
                    arrTime=arrTime+inf.getTimeFromPrevious().getMinutes()+inf.getTimeFromPrevious().getHours()*60;
                }
                Date arrivalTime = strToTime(intToTime(arrTime));
                List<Date> dateList = new ArrayList<>();
                dateList.add(departureTime);
                dateList.add(arrivalTime);
                result.put(schedule.get(i),dateList);
            }

        }
        return result;
    }

    private String intToTime(int intTime){
        String startTime = "00:00";
        int h = intTime / 60 + Integer.parseInt(startTime.substring(0,1));
        int m = intTime % 60 + Integer.parseInt(startTime.substring(3,4));
        String newtime = h+":"+m;
        return newtime;
    }

    private Date strToTime(String strTime) throws ParseException {
        DateFormat df = new SimpleDateFormat("hh:mm");
        Date result =  df.parse(strTime);
        return result;
    }
}
