package com.tsystems.trainsProject.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Basic;
import java.sql.Timestamp;

@Entity
@Table(name = "train_schedule", schema = "trains", catalog = "")
public class ScheduleEntity {
    private int idTrainSchedule;
    private Timestamp arrivalTime;
    private Timestamp departureTime;

    @Id
    @Column(name = "id_train_schedule", nullable = false)
    public int getIdTrainSchedule() {
        return idTrainSchedule;
    }

    public void setIdTrainSchedule(int idTrainSchedule) {
        this.idTrainSchedule = idTrainSchedule;
    }

    @Basic
    @Column(name = "arrival_time", nullable = true)
    public Timestamp getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(Timestamp arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    @Basic
    @Column(name = "departure_time", nullable = true)
    public Timestamp getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(Timestamp departureTime) {
        this.departureTime = departureTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ScheduleEntity that = (ScheduleEntity) o;

        if (idTrainSchedule != that.idTrainSchedule) return false;
        if (arrivalTime != null ? !arrivalTime.equals(that.arrivalTime) : that.arrivalTime != null) return false;
        if (departureTime != null ? !departureTime.equals(that.departureTime) : that.departureTime != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = idTrainSchedule;
        result = 31 * result + (arrivalTime != null ? arrivalTime.hashCode() : 0);
        result = 31 * result + (departureTime != null ? departureTime.hashCode() : 0);
        return result;
    }
}
