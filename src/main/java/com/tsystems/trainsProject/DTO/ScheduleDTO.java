package com.tsystems.trainsProject.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.tsystems.trainsProject.models.BranchLineEntity;
import com.tsystems.trainsProject.models.StationEntity;
import com.tsystems.trainsProject.models.TicketEntity;
import com.tsystems.trainsProject.models.TrainEntity;
import org.springframework.format.annotation.DateTimeFormat;

import java.sql.Time;
import java.util.Date;
import java.util.List;

public class ScheduleDTO {
    @JsonProperty("idSchedule")
    private int idSchedule;
    @DateTimeFormat(pattern = "HH:mm")
    @JsonProperty("departureTime")
    private Time departureTime;
    @JsonProperty("train")
    private String train;
    @JsonProperty("branch")
    private String branch;
    @JsonProperty("firstStation")
    private String firstStation;
    @JsonProperty("lastStation")
    private String lastStation;

    public ScheduleDTO(int idSchedule, Time departureTime, String train, String branch, String firstStation, String lastStation) {
        this.idSchedule = idSchedule;
        this.departureTime = departureTime;
        this.train = train;
        this.branch = branch;
        this.firstStation = firstStation;
        this.lastStation = lastStation;
    }

    public int getIdSchedule() {
        return idSchedule;
    }

    public void setIdSchedule(int idSchedule) {
        this.idSchedule = idSchedule;
    }

    public Time getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(Time departureTime) {
        this.departureTime = departureTime;
    }

    public String getTrain() {
        return train;
    }

    public void setTrain(String train) {
        this.train = train;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public String getFirstStation() {
        return firstStation;
    }

    public void setFirstStation(String firstStation) {
        this.firstStation = firstStation;
    }

    public String getLastStation() {
        return lastStation;
    }

    public void setLastStation(String lastStation) {
        this.lastStation = lastStation;
    }

}
