package com.tsystems.trainsProject.services.impl;

import com.tsystems.trainsProject.dto.Search;
import com.tsystems.trainsProject.dto.VariantDto;
import com.tsystems.trainsProject.models.*;
import com.tsystems.trainsProject.services.InfBranchService;
import com.tsystems.trainsProject.services.ScheduleService;
import com.tsystems.trainsProject.services.StationService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.util.*;

@Service
@Transactional
public class SearchTrain {

    private static final Logger logger = Logger.getLogger(SearchTrain.class);

    @Autowired
    StationService stationService;

    @Autowired
    InfBranchService infBranchService;

    @Autowired
    ScheduleService scheduleService;

    public Map<ScheduleEntity, List<Date>> search(Search search, BindingResult bindingResult) {
        logger.info("SearchTrain: start to search train in schedule");
        Map<ScheduleEntity, List<Date>> result = new HashMap<>();
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
            result = evaluateTime(schedule, time1, time2, departureStation, arrivalStation);
            logger.info("SearchTrain:  train has been found");
        } catch (ParseException e) {
            logger.info(e.getMessage());
            bindingResult.rejectValue("departureTimeFrom", "The time was entered incorrectly");
        }
        return result;
    }

    private List<BranchLineEntity> findCoincidingBranches(List<DetailedInfBranchEntity> listDepStations,
                                                          List<DetailedInfBranchEntity> listArrStations) {
        List<BranchLineEntity> result = new ArrayList<>();
        for (int i = 0; i < listDepStations.size(); i++) {
            for (int j = 0; j < listArrStations.size(); j++) {
                if (listDepStations.get(i).getBranch().equals(listArrStations.get(j).getBranch())) {
                    result.add(listDepStations.get(i).getBranch());
                }
            }
        }
        return result;
    }

    private Map<ScheduleEntity, List<Date>> evaluateTime(List<ScheduleEntity> schedule,
                                                         Date datetime1, Date datetime2,
                                                         StationEntity firstStation,
                                                         StationEntity lastStation) {
        logger.info("SearchTrain: start to evaluate time");
        LocalTime time2 = LocalDateTime.ofInstant(datetime2.toInstant(), ZoneId.systemDefault()).toLocalTime();
        time2 = time2.plusMinutes(1);
        LocalTime time1 = LocalDateTime.ofInstant(datetime1.toInstant(), ZoneId.systemDefault()).toLocalTime();
        time1 = time1.minusMinutes(1);
        Map<ScheduleEntity, List<Date>> result = new HashMap<>();
        for (int i = 0; i < schedule.size(); i++) {
            List<DetailedInfBranchEntity> detailedInf = infBranchService.findDetailedInformation(schedule.get(i).getBranch());
            int numberFirstStation = 0;
            int numberLastStation = 0;
            LocalTime departureTime = LocalDateTime.ofInstant(schedule.get(i).getDepartureTime().toInstant(), ZoneId.systemDefault()).toLocalTime();
            if (departureTime.isAfter(time1) && departureTime.isBefore(time2)) {
                for (int j = 0; j < detailedInf.size(); j++) {
                    if (detailedInf.get(j).getStation().equals(firstStation)) {
                        numberFirstStation = detailedInf.get(j).getStationSerialNumber();
                    }
                    if (detailedInf.get(j).getStation().equals(lastStation)) {
                        numberLastStation = detailedInf.get(j).getStationSerialNumber();
                    }
                }
                for (int y = 0 + 1; y <= numberFirstStation; y++) {
                    DetailedInfBranchEntity inf = infBranchService.findBySerialNumberStationAndSchedule(y, detailedInf.get(0).getBranch());
                    LocalTime timeFromPrevious = LocalDateTime.ofInstant(inf.getTimeFromPrevious().toInstant(), ZoneId.systemDefault()).toLocalTime();
                    departureTime = departureTime.plusHours(timeFromPrevious.getHour());
                    departureTime = departureTime.plusMinutes(timeFromPrevious.getMinute());
                }
                LocalTime arrivalTime = departureTime;
                for (int y = numberFirstStation + 1; y <= numberLastStation; y++) {
                    DetailedInfBranchEntity inf = infBranchService.findBySerialNumberStationAndSchedule(y, detailedInf.get(0).getBranch());
                    LocalTime timeFromPrevious = LocalDateTime.ofInstant(inf.getTimeFromPrevious().toInstant(), ZoneId.systemDefault()).toLocalTime();
                    arrivalTime = arrivalTime.plusHours(timeFromPrevious.getHour());
                    arrivalTime = arrivalTime.plusMinutes(timeFromPrevious.getMinute());
                }
                Instant instant = departureTime.atDate(LocalDate.now()).
                        atZone(ZoneId.systemDefault()).toInstant();
                Date depTime = Date.from(instant);
                List<Date> dateList = new ArrayList<>();
                dateList.add(depTime);
                Instant instant2 = arrivalTime.atDate(LocalDate.now()).
                        atZone(ZoneId.systemDefault()).toInstant();
                Date arrTime = Date.from(instant2);
                dateList.add(arrTime);
                result.put(schedule.get(i), dateList);
            }

        }
        logger.info("SearchTrain: time  has ben evaluated");
        return result;
    }

    public VariantDto getVariant(int id, List<VariantDto> variants) {
        VariantDto variant = new VariantDto();
        for (int i = 0; i < variants.size(); i++) {
            if (variants.get(i).getIdVariant() == id) {
                variant = variants.get(i);
            }
        }
        return variant;
    }
}
