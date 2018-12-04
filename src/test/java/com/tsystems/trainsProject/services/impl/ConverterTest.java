package com.tsystems.trainsProject.services.impl;

import com.tsystems.trainsProject.dao.ScheduleDAO;
import com.tsystems.trainsProject.dto.Converter;
import com.tsystems.trainsProject.models.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class ConverterTest {

    @InjectMocks
    private Converter converter;

    @Test
    public void convertSchedule() throws ParseException {
        ScheduleEntity schedule=getSchedule();
        assertEquals(schedule.getBranch().getTitle(), converter.convertSchedule(schedule).getBranch());
        assertEquals(schedule.getDepartureTime(), converter.convertSchedule(schedule).getDepartureTime());
        assertEquals(schedule.getFirstStation().getStationName(), converter.convertSchedule(schedule).getFirstStation());
        assertEquals(schedule.getLastStation().getStationName(), converter.convertSchedule(schedule).getLastStation());
        assertEquals(schedule.getIdSchedule(), converter.convertSchedule(schedule).getIdSchedule());
        assertEquals(schedule.getTrain().getNumber(), converter.convertSchedule(schedule).getTrain());
    }

    private ScheduleEntity getSchedule() throws ParseException {
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
    public void convertStation() {
        StationEntity station = new StationEntity();
        station.setStationName("1");
        assertEquals(station.getStationName(), converter.convertStation(station).getStationName());
    }

    @Test
    public void convertUser() {
        UserEntity userEntity = new UserEntity();
        RoleEntity roleEntity=new RoleEntity();
        roleEntity.setTitle("1");
        userEntity.setRole(roleEntity);
        userEntity.setLogin("1");
        assertEquals(userEntity.getRole().getTitle(), converter.convertUser(userEntity).getRole());
        assertEquals(userEntity.getLogin(), converter.convertUser(userEntity).getLogin());
    }

    @Test
    public void convertTicket() throws ParseException {
        ScheduleEntity schedule=getSchedule();
        UserEntity user = new UserEntity();
        user.setLogin("1");
        PassangerEntity passanger = new PassangerEntity();
        passanger.setDateOfBirth(new Date());
        passanger.setSurname("1");
        passanger.setPatronymic("1");
        passanger.setName("1");
        passanger.setUser(user);
        TicketEntity ticket = new TicketEntity();
        ticket.setSeat(1);
        ticket.setFirstStation(schedule.getBranch().getDetailedInf().get(0).getStation());
        ticket.setLastStation(schedule.getBranch().getDetailedInf().get(1).getStation());
        ticket.setSchedule(schedule);
        ticket.setDepartureDate(new Date());
        ticket.setArrivalTime(new Date());
        ticket.setDepartureTime(new Date());
        ticket.setIdTicket(1);
        ticket.setPassanger(passanger);

        assertEquals(ticket.getIdTicket(), converter.convertTicket(ticket).getIdTicket());
        assertEquals(ticket.getSeat(), converter.convertTicket(ticket).getSeat());
        assertEquals(ticket.getPassanger().getName(), converter.convertTicket(ticket).getName());
        assertEquals(ticket.getPassanger().getSurname(), converter.convertTicket(ticket).getSurname());
        assertEquals(ticket.getPassanger().getPatronymic(), converter.convertTicket(ticket).getPatronymic());
        assertEquals(ticket.getSchedule().getTrain().getNumber(), converter.convertTicket(ticket).getTrain());
        assertEquals(ticket.getFirstStation().getStationName(), converter.convertTicket(ticket).getFirstStation());
        assertEquals(ticket.getLastStation().getStationName(), converter.convertTicket(ticket).getLastStation());
    }

}
