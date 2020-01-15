package com.tsystems.trainsProject.services.impl;

import com.tsystems.trainsProject.models.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.validation.BindingResult;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class TicketServiceTest {

    @InjectMocks
    private TicketServiceImpl ticketService;

    private ScheduleEntity getSchedule() {
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
        return schedule;
    }

    @Test
    public void validation() {
        ScheduleEntity schedule = getSchedule();
        schedule.setProhibitPurchase(new Date());
        BindingResult bindingResult = mock(BindingResult.class);
        TicketEntity ticket = new TicketEntity();
        PassangerEntity passanger = new PassangerEntity();
        passanger.setName("1");
        passanger.setPatronymic("1");
        passanger.setSurname("1");
        passanger.setDateOfBirth(new Date());
        ticket.setSeat(1);
        ticket.setFirstStation(schedule.getBranch().getDetailedInf().get(0).getStation());
        ticket.setLastStation(schedule.getBranch().getDetailedInf().get(2).getStation());
        ticket.setSchedule(schedule);
        ticket.setDepartureDate(new Date());
        ticket.setPassanger(passanger);

        TicketEntity ticketCheck = new TicketEntity();
        ticketCheck.setFirstStation(schedule.getBranch().getDetailedInf().get(0).getStation());
        ticketCheck.setLastStation(schedule.getBranch().getDetailedInf().get(3).getStation());
        ticketCheck.setSchedule(schedule);
        ticketCheck.setDepartureDate(new Date());
        ticketCheck.setDepartureTime(new Date());
        ticketCheck.setPassanger(passanger);
        List<TicketEntity> tickets = new ArrayList<>();
        tickets.add(ticket);
        tickets.add(ticketCheck);
        schedule.setTicket(tickets);

        ticketService.validation(bindingResult, ticketCheck);
        verify(bindingResult, times(3)).addError(any());
    }


}

