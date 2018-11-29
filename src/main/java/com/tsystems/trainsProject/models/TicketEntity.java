package com.tsystems.trainsProject.models;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "ticket", schema = "trains", catalog = "")
public class TicketEntity implements Comparable<TicketEntity>{
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idTicket;
    @DateTimeFormat(pattern = "HH:mm")
    private Date departureTime;
    @DateTimeFormat(pattern = "HH:mm")
    private Date arrivalTime;
    private int seat;
    private PassangerEntity passanger;
    private ScheduleEntity schedule;
    private StationEntity firstStation;
    private StationEntity lastStation;
    @DateTimeFormat(pattern = "HH:mm")
    private Date journeyTime;
    @DateTimeFormat(pattern = "yyyy-M-dd")
    private Date departureDate;

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

    @LazyCollection(LazyCollectionOption.FALSE)
    @ManyToOne
    @JoinColumn(name = "id_schedule", referencedColumnName = "id_schedule")
    public ScheduleEntity getSchedule() {
        return schedule;
    }

    public void setSchedule(ScheduleEntity schedule) {
        this.schedule = schedule;
    }

    @Cascade({org.hibernate.annotations.CascadeType.ALL})
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
    public int getSeat() {
        return seat;
    }

    public void setSeat(int seat) {
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

    @Basic
    @Column(name = "departure_date", nullable = true)
    public Date getDepartureDate() {
        return departureDate;
    }

    public void setDepartureDate(Date departureDate) {
        this.departureDate = departureDate;
    }

    @Override
    public int compareTo(TicketEntity ticketEntity) {
        return departureDate.compareTo(ticketEntity.getDepartureDate());
    }
}
