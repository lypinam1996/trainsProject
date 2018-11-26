package com.tsystems.trainsProject.dto;

import com.tsystems.trainsProject.models.PassangerEntity;
import com.tsystems.trainsProject.models.ScheduleEntity;
import com.tsystems.trainsProject.models.StationEntity;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

public class VariantDto {
    private int idVariant;
    private Date departureTime;
    private Date arrivalTime;
    private ScheduleEntity schedule;
    private StationEntity firstStation;
    private StationEntity lastStation;
    private Date journeyTime;

    public VariantDto() {
    }

    public VariantDto(int idVariant, Date departureTime, Date arrivalTime,
                      ScheduleEntity schedule, StationEntity firstStation,
                      StationEntity lastStation, Date journeyTime) {
        this.idVariant = idVariant;
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
        this.schedule = schedule;
        this.firstStation = firstStation;
        this.lastStation = lastStation;
        this.journeyTime = journeyTime;
    }

    public int getIdVariant() {
        return idVariant;
    }

    public void setIdVariant(int idVariant) {
        this.idVariant = idVariant;
    }

    public Date getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(Date departureTime) {
        this.departureTime = departureTime;
    }

    public Date getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(Date arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public ScheduleEntity getSchedule() {
        return schedule;
    }

    public void setSchedule(ScheduleEntity schedule) {
        this.schedule = schedule;
    }

    public StationEntity getFirstStation() {
        return firstStation;
    }

    public void setFirstStation(StationEntity firstStation) {
        this.firstStation = firstStation;
    }

    public StationEntity getLastStation() {
        return lastStation;
    }

    public void setLastStation(StationEntity lastStation) {
        this.lastStation = lastStation;
    }

    public Date getJourneyTime() {
        return journeyTime;
    }

    public void setJourneyTime(Date journeyTime) {
        this.journeyTime = journeyTime;
    }

}
