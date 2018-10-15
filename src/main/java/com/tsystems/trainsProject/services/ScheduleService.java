package com.tsystems.trainsProject.services;


import com.tsystems.trainsProject.models.BranchLineEntity;
import com.tsystems.trainsProject.models.DetailedInfBranchEntity;
import com.tsystems.trainsProject.models.ScheduleEntity;
import com.tsystems.trainsProject.models.StationEntity;

import java.util.List;
import java.util.Map;

public interface ScheduleService {
    List<ScheduleEntity> findAllSchedules();
    List<ScheduleEntity> findScheduleByBranch(BranchLineEntity branchLine);
   // List<ScheduleEntity> findSchedulesByBranch(List<BranchLineEntity> branchLines);
    List<ScheduleEntity> findSchedulesByBranch(Map<BranchLineEntity,List<DetailedInfBranchEntity>> detailedInfBranchList,
                                                      StationEntity firstStation,
                                                      StationEntity lastStation);
    void saveOrUpdate(ScheduleEntity schedule);
    String checkStationsSerialNumbers(ScheduleEntity schedule);
    ScheduleEntity findById(int id);
    void delete(ScheduleEntity schedule);
}
