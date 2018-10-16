package com.tsystems.trainsProject.models;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "schedule", schema = "trains")
public class ScheduleEntity {
    private int idSchedule;
    @DateTimeFormat(pattern = "HH:mm")
    private Date departureTime;
    private TrainEntity train;
    private BranchLineEntity branch;
    private StationEntity firstStation;
    private StationEntity lastStation;
    private List<TicketEntity> ticket;

    @OneToMany(mappedBy = "schedule")
    public List<TicketEntity> getTicket() {
        return ticket;
    }

    public void setTicket(List<TicketEntity> ticket) {
        this.ticket = ticket;
    }

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
    @JoinColumn(name = "id_branch", referencedColumnName = "id_branch_line")
    public BranchLineEntity getBranch() {
        return branch;
    }

    public void setBranch(BranchLineEntity branch) {
        this.branch = branch;
    }

    @LazyCollection(LazyCollectionOption.FALSE)
    @ManyToOne
    @JoinColumn(name = "id_train", referencedColumnName = "id_train")
    public TrainEntity getTrain() {
        return train;
    }

    public void setTrain(TrainEntity train) {
        this.train = train;
    }

    @Id
    @Column(name = "id_schedule", nullable = false)
    public int getIdSchedule() {
        return idSchedule;
    }

    public void setIdSchedule(int idSchedule) {
        this.idSchedule = idSchedule;
    }

    @Basic
    @Column(name = "departure_time", nullable = true)
    public Date getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(Date departureTime) {
        this.departureTime = departureTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ScheduleEntity that = (ScheduleEntity) o;

        if (idSchedule != that.idSchedule) return false;
        if (departureTime != null ? !departureTime.equals(that.departureTime) : that.departureTime != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = idSchedule;
        result = 31 * result + (departureTime != null ? departureTime.hashCode() : 0);
        return result;
    }
}
