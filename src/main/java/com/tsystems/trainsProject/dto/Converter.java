package com.tsystems.trainsProject.dto;

import com.tsystems.trainsProject.models.ScheduleEntity;
import com.tsystems.trainsProject.models.StationEntity;
import com.tsystems.trainsProject.models.TicketEntity;
import com.tsystems.trainsProject.models.UserEntity;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class Converter {
    public ScheduleDTO convertSchedule(ScheduleEntity scheduleEntity) throws ParseException {
        String pattern = "HH:mm";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        String time = simpleDateFormat.format(scheduleEntity.getDepartureTime());
        DateFormat formatter = new SimpleDateFormat(pattern);
        java.sql.Time time1 = new java.sql.Time(formatter.parse(time).getTime());
        ScheduleDTO scheduleDTO = new ScheduleDTO(scheduleEntity.getIdSchedule(),
                time1,
                scheduleEntity.getTrain().getNumber(),
                scheduleEntity.getBranch().getTitle(),
                scheduleEntity.getFirstStation().getStationName(),
                scheduleEntity.getLastStation().getStationName()
               );
        return scheduleDTO;
    }

    public StationDTO convertStation(StationEntity stationEntity) {
        StationDTO stationDTO = new StationDTO(stationEntity.getIdStation(),
                stationEntity.getStationName()
        );
        return stationDTO;
    }

    public UserDTO convertUser(UserEntity user) {
        UserDTO userDTO = new UserDTO(user.getLogin(),user.getRole().getTitle());
        return userDTO;
    }

    public TicketDto convertTicket(TicketEntity ticket) {
        String pattern = "HH:mm";
        DateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        String pattern2 = "dd.mm.yyyy";
        DateFormat dateFormat = new SimpleDateFormat(pattern2);
        String login = ticket.getPassanger().getUser().getLogin();
        String depTime="";
        if(ticket.getDepartureTime()!=null){
            depTime=simpleDateFormat.format(  ticket.getDepartureTime());
        }
        String arrTime="";
        if(ticket.getArrivalTime()!=null){
            arrTime=simpleDateFormat.format(  ticket.getArrivalTime());
        }
        String name ="";
        String surname ="";
        String patronymic ="";
        String dateOfBirth ="";
        if(ticket.getPassanger()!=null){
            if( ticket.getPassanger().getName()!=null){
                name= ticket.getPassanger().getName();
            }
            if( ticket.getPassanger().getSurname()!=null){
                surname= ticket.getPassanger().getSurname();
            }
            if( ticket.getPassanger().getPatronymic()!=null){
                patronymic= ticket.getPassanger().getPatronymic();
            }
            if( ticket.getPassanger().getDateOfBirth()!=null){
                dateOfBirth= ticket.getPassanger().getDateOfBirth().toString();
            }
        }
        String train ="";
        if(ticket.getSchedule()!=null){
            if( ticket.getSchedule().getTrain()!=null){
                if( ticket.getSchedule().getTrain().getNumber()!=null){
                    train=ticket.getSchedule().getTrain().getNumber();
                }
            }
        }
        String firstStation="";
        if(ticket.getFirstStation()!=null){
            if(ticket.getFirstStation().getStationName()!=null){
                firstStation  =ticket.getFirstStation().getStationName();
            }
        }
        String lastStation="";
        if(ticket.getLastStation()!=null){
            if(ticket.getLastStation().getStationName()!=null){
                lastStation  =ticket.getLastStation().getStationName();
            }
        }
        String journeyTime="";
        if(ticket.getJourneyTime()!=null){
            journeyTime=simpleDateFormat.format(  ticket.getJourneyTime());
        }
        String departureDate="";
        if(ticket.getDepartureDate()!=null){
            departureDate=dateFormat.format(  ticket.getDepartureDate());
        }
        TicketDto ticketDto = new TicketDto(ticket.getIdTicket(), depTime,
                arrTime, ticket.getSeat(), name, surname,
                patronymic, dateOfBirth,train, firstStation,
                lastStation, journeyTime, departureDate, login);
        return ticketDto;
    }


}
