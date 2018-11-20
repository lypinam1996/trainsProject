package com.tsystems.trainsProject.DTO;

import com.tsystems.trainsProject.dao.StationDAO;
import com.tsystems.trainsProject.models.ScheduleEntity;
import com.tsystems.trainsProject.models.StationEntity;
import com.tsystems.trainsProject.models.UserEntity;
import com.tsystems.trainsProject.services.UserService;

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
}
