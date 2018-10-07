package com.tsystems.trainsProject.models;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "ticket", schema = "trains", catalog = "")
public class TicketEntity {
    private int idTicket;
    private Date departureTime;
    private Date arrivalTime;
    private String seat;
    private PassangerEntity passanger;
    private ScheduleEntity schedule;
    private StationEntity firstStation;
    private StationEntity lastStation;
    private Date journeyTime;

    @ManyToOne
    @JoinColumn(name = "id_first_station", referencedColumnName = "id_station")
    public StationEntity getFirstStation() {
        return firstStation;
    }

    public void setFirstStation(StationEntity firstStation) {
        this.firstStation = firstStation;
    }

    @ManyToOne
    @JoinColumn(name = "id_last_station", referencedColumnName = "id_station")
    public StationEntity getLastStation() {
        return lastStation;
    }

    public void setLastStation(StationEntity lastStation) {
        this.lastStation = lastStation;
    }

    @ManyToOne
    @JoinColumn(name = "id_schedule", referencedColumnName = "id_schedule")
    public ScheduleEntity getSchedule() {
        return schedule;
    }

    public void setSchedule(ScheduleEntity schedule) {
        this.schedule = schedule;
    }

    @ManyToOne
    @JoinColumn(name = "id_passanger", referencedColumnName = "id_passanger")
    public PassangerEntity getPassanger() {
        return passanger;
    }

    public void setPassanger(PassangerEntity passanger) {
        this.passanger = passanger;
    }

    @Id
    @Column(name = "id_ticket", nullable = false)
    public int getIdTicket() {
        return idTicket;
    }

    public void setIdTicket(int idTicket) {
        this.idTicket = idTicket;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TicketEntity that = (TicketEntity) o;

        if (idTicket != that.idTicket) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return idTicket;
    }

    @Basic
    @Column(name = "departure_time", nullable = true)
    public Date getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(Date departureTime) {
        this.departureTime = departureTime;
    }

    @Basic
    @Column(name = "arrival_time", nullable = true)
    public Date getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(Date arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    @Basic
    @Column(name = "seat", nullable = true, length = 45)
    public String getSeat() {
        return seat;
    }

    public void setSeat(String seat) {
        this.seat = seat;
    }

    @Basic
    @Column(name = "journey_time", nullable = true)
    public Date getJourneyTime() {
        return journeyTime;
    }

    public void setJourneyTime(Date journeyTime) {
        this.journeyTime = journeyTime;
    }
}
