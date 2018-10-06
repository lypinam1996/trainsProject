package com.tsystems.trainsProject.services.impl;

import com.tsystems.trainsProject.models.*;
import com.tsystems.trainsProject.services.InfBranchService;
import com.tsystems.trainsProject.services.ScheduleService;
import com.tsystems.trainsProject.services.StationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;

import java.sql.Time;
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

    public List<ScheduleEntity> search(Search search, BindingResult bindingResult)  {
        List<ScheduleEntity> result = new ArrayList<>();
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
            result = evaluateTime(schedule, time1, time2, departureStation);
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

    private List<ScheduleEntity> evaluateTime(List<ScheduleEntity> schedule,
                                              Date time1, Date time2,
                                              StationEntity firstStation){

        List<ScheduleEntity> result = new ArrayList<>();
        for(int i=0;i<schedule.size();i++) {
            Date depTime = schedule.get(i).getDepartureTime();
            List<DetailedInfBranchEntity> detailedInf = infBranchService.findDetailedInformation(schedule.get(i).getBranch());
            int numberFirstStation = 0;
            for (int j = 0; j < detailedInf.size(); j++) {
                if (detailedInf.get(j).getStation().equals(firstStation)) {
                    numberFirstStation = detailedInf.get(j).getStationSerialNumber();
                }
            }
            int count = 0;
            boolean ok=true;
            int j = numberFirstStation;
            while (j < detailedInf.size() && ok) {
                for (int x=0;x < detailedInf.size();x++){
                    if(detailedInf.get(x).getStationSerialNumber()==numberFirstStation+count){
                       if(depTime.after(time1) && depTime.before(time2)){
                            result.add(schedule.get(i));
                            ok=false;
                         }
                    }
                }
                count++;
                j++;
            }
        }
        return result;
    }
}
