package com.tsystems.trainsProject.services.impl;

import com.tsystems.trainsProject.dao.ScheduleDAO;
import com.tsystems.trainsProject.models.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.validation.BindingResult;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@RunWith(MockitoJUnitRunner.class)
public class ScheduleServiceTest {

    @InjectMocks
    private ScheduleServiceImpl service;

    @Mock
    private ScheduleDAO scheduleDAO;

    @Test
    public void checkfindAllSchedulesAfterTime() throws ParseException {
        ScheduleEntity schedule = getSchedule1();
        List<ScheduleEntity> list = Arrays.asList(schedule);
        when(scheduleDAO.findAllSchedules()).thenReturn(list);
        assertEquals(list, service.findAllSchedulesAfterTime());

    }

    private ScheduleEntity getSchedule1() throws ParseException {
        TrainEntity train = new TrainEntity();
        train.setNumber("1");
        train.setNumberOfSeats(3);

        StationEntity station1 = new StationEntity();
        station1.setStationName("1");
        StationEntity station2 = new StationEntity();
        station2.setStationName("2");
        StationEntity station3 = new StationEntity();
        station3.setStationName("3");
        StationEntity station4 = new StationEntity();
        station4.setStationName("4");

        BranchLineEntity branch = new BranchLineEntity();
        branch.setTitle("branch");

        DetailedInfBranchEntity infBranch1 = new DetailedInfBranchEntity();
        infBranch1.setStation(station1);
        infBranch1.setStationSerialNumber(1);
        DetailedInfBranchEntity infBranch2 = new DetailedInfBranchEntity();
        infBranch2.setStation(station2);
        infBranch2.setStationSerialNumber(2);
        DetailedInfBranchEntity infBranch3 = new DetailedInfBranchEntity();
        infBranch3.setStation(station3);
        infBranch3.setStationSerialNumber(3);
        DetailedInfBranchEntity infBranch4 = new DetailedInfBranchEntity();
        infBranch4.setStation(station4);
        infBranch4.setStationSerialNumber(4);
        List<DetailedInfBranchEntity> list = new ArrayList<>();
        list.add(infBranch1);
        list.add(infBranch2);
        list.add(infBranch3);
        list.add(infBranch4);

        branch.setDetailedInf(list);

        ScheduleEntity schedule = new ScheduleEntity();
        schedule.setBranch(branch);
        schedule.setFirstStation(station1);
        schedule.setLastStation(station4);
        schedule.setTrain(train);
        DateFormat df = new SimpleDateFormat("HH:mm");
        Date date = df.parse("23:59:59");
        schedule.setDepartureTime(date);
        return schedule;
    }

    @Test
    public void validation() throws ParseException {
        BindingResult bindingResult=mock(BindingResult.class);
        ScheduleEntity schedule = getSchedule2();
        service.validation(schedule,bindingResult);
        verify(bindingResult, times(1)).addError(any());
    }

    private ScheduleEntity getSchedule2() throws ParseException {
        TrainEntity train = new TrainEntity();
        train.setNumber("1");
        train.setNumberOfSeats(3);

        StationEntity station1 = new StationEntity();
        station1.setStationName("1");
        StationEntity station2 = new StationEntity();
        station2.setStationName("2");
        StationEntity station3 = new StationEntity();
        station3.setStationName("3");
        StationEntity station4 = new StationEntity();
        station4.setStationName("4");

        BranchLineEntity branch = new BranchLineEntity();
        branch.setTitle("branch");

        DetailedInfBranchEntity infBranch1 = new DetailedInfBranchEntity();
        infBranch1.setStation(station1);
        infBranch1.setStationSerialNumber(1);
        DetailedInfBranchEntity infBranch2 = new DetailedInfBranchEntity();
        infBranch2.setStation(station2);
        infBranch2.setStationSerialNumber(2);
        DetailedInfBranchEntity infBranch3 = new DetailedInfBranchEntity();
        infBranch3.setStation(station3);
        infBranch3.setStationSerialNumber(3);
        DetailedInfBranchEntity infBranch4 = new DetailedInfBranchEntity();
        infBranch4.setStation(station4);
        infBranch4.setStationSerialNumber(4);
        List<DetailedInfBranchEntity> list = new ArrayList<>();
        list.add(infBranch1);
        list.add(infBranch2);
        list.add(infBranch3);
        list.add(infBranch4);

        branch.setDetailedInf(list);

        ScheduleEntity schedule = new ScheduleEntity();
        schedule.setBranch(branch);
        schedule.setFirstStation(station4);
        schedule.setLastStation(station1);
        schedule.setTrain(train);
        schedule.setDepartureTime(null);
        return schedule;
    }
}

