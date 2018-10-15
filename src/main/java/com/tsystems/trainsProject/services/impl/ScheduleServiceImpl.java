package com.tsystems.trainsProject.services.impl;
import com.tsystems.trainsProject.dao.impl.InfBranchDAOImpl;
import com.tsystems.trainsProject.dao.impl.ScheduleDAOImpl;
import com.tsystems.trainsProject.models.BranchLineEntity;
import com.tsystems.trainsProject.models.DetailedInfBranchEntity;
import com.tsystems.trainsProject.models.ScheduleEntity;
import com.tsystems.trainsProject.models.StationEntity;
import com.tsystems.trainsProject.services.ScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service("ScheduleService")
@Transactional
public class ScheduleServiceImpl implements ScheduleService {

    @Autowired
    ScheduleDAOImpl scheduleDAO;

    @Autowired
    InfBranchDAOImpl infBranchDAO;

    @Override
    public List<ScheduleEntity> findAllSchedules() {
        List<ScheduleEntity> res =scheduleDAO.findAllSchedules();
        return res;
    }

    @Override
    public List<ScheduleEntity> findScheduleByBranch(BranchLineEntity branchLine) {
        return scheduleDAO.findSchedulesByBranch(branchLine);
    }


    public List<ScheduleEntity> findSchedulesByBranch(Map<BranchLineEntity,
            List<DetailedInfBranchEntity>> detailedInfBranchList,
                                                      StationEntity firstStation,
                                                      StationEntity lastStation){
        List<ScheduleEntity> result = new ArrayList<>();
        for (Map.Entry<BranchLineEntity, List<DetailedInfBranchEntity>> detailedInf : detailedInfBranchList.entrySet()) {
            List<ScheduleEntity> schedules = findScheduleByBranch(detailedInf.getKey());
            for(int i=0;i<schedules.size();i++){
                StationEntity firstStationInSchedule = schedules.get(i).getFirstStation();
                StationEntity lastStationInSchedule = schedules.get(i).getLastStation();
                int numberFirstStationInSchedule=0;
                int numberFirstStation=0;
                int numberLastStationInSchedule=0;
                int numberLastStation=0;
                for(int j=0;j<detailedInf.getValue().size();j++) {
                    if(detailedInf.getValue().get(j).getStation().equals(firstStationInSchedule)){
                        numberFirstStationInSchedule=detailedInf.getValue().get(j).getStationSerialNumber();
                    }
                    if(detailedInf.getValue().get(j).getStation().equals(firstStation)){
                        numberFirstStation=detailedInf.getValue().get(j).getStationSerialNumber();
                    }
                    if(detailedInf.getValue().get(j).getStation().equals(lastStationInSchedule)){
                        numberLastStationInSchedule=detailedInf.getValue().get(j).getStationSerialNumber();
                    }
                    if(detailedInf.getValue().get(j).getStation().equals(lastStation)){
                        numberLastStation=detailedInf.getValue().get(j).getStationSerialNumber();
                    }
                }
                if(numberFirstStationInSchedule<=numberFirstStation && numberLastStationInSchedule>=numberLastStation){
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

    @Override
    public String checkStationsSerialNumbers(ScheduleEntity schedule){
        String error = "";
        BranchLineEntity branch = schedule.getBranch();
        StationEntity firstStation = schedule.getFirstStation();
        StationEntity lastStation = schedule.getLastStation();
        int numberFistStation=0;
        int numberLastStation=0;
        for(int i=0;i<branch.getDetailedInf().size();i++){
            if(branch.getDetailedInf().get(i).getStation().equals(firstStation)){
                numberFistStation=branch.getDetailedInf().get(i).getStationSerialNumber();
            }
            if(branch.getDetailedInf().get(i).getStation().equals(lastStation)){
                numberLastStation=branch.getDetailedInf().get(i).getStationSerialNumber();
            }
        }
        if(numberFistStation>numberLastStation){
            error="*Error in entering stations";
        }
        return error;
    }

    @Override
    public ScheduleEntity findById(int id) {
        return scheduleDAO.findById(id);
    }


}
