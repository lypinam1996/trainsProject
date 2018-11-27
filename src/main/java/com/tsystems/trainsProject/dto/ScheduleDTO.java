package com.tsystems.trainsProject.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.format.annotation.DateTimeFormat;

import java.sql.Time;
import java.time.LocalTime;
import java.util.Date;

public class ScheduleDTO {
    @JsonProperty("idSchedule")
    private int idSchedule;
    @JsonProperty("departureTime")
    private Date departureTime;
    @JsonProperty("train")
    private String train;
    @JsonProperty("branch")
    private String branch;
    @JsonProperty("firstStation")
    private String firstStation;
    @JsonProperty("lastStation")
    private String lastStation;

    public ScheduleDTO(int idSchedule, Date departureTime, String train, String branch, String firstStation, String lastStation) {
        this.idSchedule = idSchedule;
        this.departureTime = departureTime;
        this.train = train;
        this.branch = branch;
        this.firstStation = firstStation;
        this.lastStation = lastStation;
    }

    public ScheduleDTO() {
    }

    public int getIdSchedule() {
        return idSchedule;
    }

    public void setIdSchedule(int idSchedule) {
        this.idSchedule = idSchedule;
    }

    public Date getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(Date departureTime) {
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
