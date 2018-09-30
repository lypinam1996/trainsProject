package com.tsystems.trainsProject.controllers;

import com.tsystems.trainsProject.models.*;
import com.tsystems.trainsProject.services.InfBranchService;
import com.tsystems.trainsProject.services.ScheduleService;
import com.tsystems.trainsProject.services.StationService;
import com.tsystems.trainsProject.services.TrainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.sql.Time;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
public class TrainController {

    @Autowired
    TrainService trainService;

    @Autowired
    StationService stationService;

    @Autowired
    InfBranchService infBranchService;

    @Autowired
    ScheduleService scheduleService;

    @RequestMapping(value = "/trains", method = RequestMethod.GET)
    public ModelAndView getAdd() {
        ModelAndView model = new ModelAndView();
        List<TrainEntity> trains = trainService.findAllTrains();
        int trainsCount = trains.size();
        model.addObject(trainsCount);
        model.addObject("trains", trains);
        model.setViewName("trains");
        return model;
    }

    @RequestMapping(value = "/serch", method = RequestMethod.GET)
    public ModelAndView getTrain() {
        ModelAndView model = new ModelAndView();
        StationEntity departureStation = stationService.findByName("C");
        StationEntity arrivalStation = stationService.findByName("D");
        List<DetailedInfBranchEntity>  listDepStations = infBranchService.findBranchesByStation(departureStation);
        List<DetailedInfBranchEntity>  listArrStations = infBranchService.findBranchesByStation(arrivalStation);
        List<BranchLineEntity> branches = findCoincidingBranches(listDepStations,listArrStations);
        Map<BranchLineEntity,List<DetailedInfBranchEntity>> detailedInfBranchList = infBranchService.findBranchesByBranches(branches);
        List<ScheduleEntity> schedule = scheduleService.findSchedulesByBranch(detailedInfBranchList,departureStation,arrivalStation);
        Time time = new Time(3);
        List<ScheduleEntity> result = evaluateTime(schedule,time,departureStation);
        model.setViewName("serch");
        return model;
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

    private List<ScheduleEntity> evaluateTime(List<ScheduleEntity> schedule, Time time, StationEntity firstStation){

        List<ScheduleEntity> result = new ArrayList<>();
        for(int i=0;i<schedule.size();i++) {
           Time depTime = schedule.get(i).getDepartureTime();
            List<DetailedInfBranchEntity> detailedInf = infBranchService.findDetailedInformation(schedule.get(i).getBranch());
            int numberFirstStation = 0;
            for (int j = 0; j < detailedInf.size(); j++) {
                if (detailedInf.get(j).getStation().equals(firstStation)) {
                    numberFirstStation = detailedInf.get(j).getStationSerialNumber();
                }
            }
            int count = 0;
            for (int j = numberFirstStation; j < detailedInf.size(); j++) {

                for (int x = 0; x < detailedInf.size(); x++) {
                    if(detailedInf.get(x).getStationSerialNumber()==numberFirstStation+count){
                        int k =depTime.getMinutes()+detailedInf.get(x).getTimeFromPrevious().getMinutes();
                        if(k>time.getMinutes()){
                            result.add(schedule.get(i));
                        }
                    }

                }
                count++;
            }
        }
        return result;
    }


}
