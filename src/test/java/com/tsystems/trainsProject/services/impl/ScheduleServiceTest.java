package com.tsystems.trainsProject.services.impl;

import com.tsystems.trainsProject.dao.ScheduleDAO;
import com.tsystems.trainsProject.models.BranchLineEntity;
import com.tsystems.trainsProject.models.DetailedInfBranchEntity;
import com.tsystems.trainsProject.models.ScheduleEntity;
import com.tsystems.trainsProject.models.StationEntity;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyCollection;
import static org.mockito.Mockito.when;


@RunWith(MockitoJUnitRunner.class)
public class ScheduleServiceTest {

    private ScheduleServiceImpl service;

    @Mock
    ScheduleDAO scheduleDAO;

    @Before
    public void initTest() {
        service = new ScheduleServiceImpl();
    }

    @Test
    public void test() {
        StationEntity firstStation = new StationEntity();
        firstStation.setIdStation(1);
        firstStation.setStationName("1");
        StationEntity lastStation = new StationEntity();
        lastStation.setIdStation(2);
        lastStation.setStationName("2");
        BranchLineEntity branch = new BranchLineEntity();
        DetailedInfBranchEntity infBranchEntity1 = new DetailedInfBranchEntity();
        infBranchEntity1.setStationSerialNumber(1);
        infBranchEntity1.setStation(firstStation);
        DetailedInfBranchEntity infBranchEntity2 = new DetailedInfBranchEntity();
        infBranchEntity2.setStationSerialNumber(2);
        infBranchEntity2.setStation(lastStation);
        List<DetailedInfBranchEntity> list = new ArrayList<>();
        list.add(infBranchEntity1);
        list.add(infBranchEntity2);
        branch.setDetailedInf(list);
        ScheduleEntity schedule = new ScheduleEntity();
        schedule.setBranch(branch);
        schedule.setFirstStation(lastStation);
        schedule.setLastStation(firstStation);
        schedule.setIdSchedule(1);
        assertEquals("*Error in entering stations", service.checkStationsSerialNumbers(schedule));
    }
}
