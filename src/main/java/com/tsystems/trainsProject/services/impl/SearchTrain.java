package com.tsystems.trainsProject.services.impl;

import com.tsystems.trainsProject.dto.Search;
import com.tsystems.trainsProject.models.*;
import com.tsystems.trainsProject.services.InfBranchService;
import com.tsystems.trainsProject.services.ScheduleService;
import com.tsystems.trainsProject.services.StationService;
import org.apache.commons.lang.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;

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
            SimpleDateFormat ft = new SimpleDateFormat("HH:mm");
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
        DateUtils.addMinutes(time2,1);
        if(time1.getMinutes()==0){
            time1.setHours(time1.getHours()-1);
            time1.setMinutes(59);
        }
        else {
            time1.setMinutes(time1.getMinutes()-1);
        }
        Map<ScheduleEntity,List<Date>> result = new HashMap<>();
        for(int i=0;i<schedule.size();i++) {
            List<DetailedInfBranchEntity> detailedInf = infBranchService.findDetailedInformation(schedule.get(i).getBranch());
            int numberFirstStation = 0;
            int numberLastStation = 0;
            Date departureTime =schedule.get(i).getDepartureTime();
            Date arrivalTime=departureTime;
            if(departureTime.after(time1) && departureTime.before(time2)){
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
                    arrivalTime=DateUtils.addHours(arrivalTime,inf.getTimeFromPrevious().getHours());
                    arrivalTime=DateUtils.addMinutes(arrivalTime,inf.getTimeFromPrevious().getMinutes());
                }
                List<Date> dateList = new ArrayList<>();
                dateList.add(departureTime);
                dateList.add(arrivalTime);
                result.put(schedule.get(i),dateList);
            }

        }
        return result;
    }
}
