package com.tsystems.trainsProject.dao;

import com.tsystems.trainsProject.models.BranchLineEntity;
import com.tsystems.trainsProject.models.ScheduleEntity;

import java.util.List;

public interface ScheduleDAO {
    List<ScheduleEntity> findAllSchedules();
    List<ScheduleEntity> findSchedulesByBranch(BranchLineEntity branchLine);
    int saveOrUpdate(ScheduleEntity schedule);
    ScheduleEntity findById(int id);
    ScheduleEntity findId(ScheduleEntity scheduleEntity);
}
