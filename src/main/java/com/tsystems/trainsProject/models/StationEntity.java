package com.tsystems.trainsProject.models;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "station", schema = "trains")
public class StationEntity {
    private int idStation;
    private String stationName;
    private List<DetailedInfBranchEntity> detailedInf;
    private List<ScheduleEntity> firstStation;
    private List<ScheduleEntity> lastStation;
    private List<TicketEntity> ticketFistStation;
    private List<TicketEntity> ticketLastStation;

    @OneToMany(mappedBy = "firstStation")
    public List<TicketEntity> getTicketFistStation() {
        return ticketFistStation;
    }

    public void setTicketFistStation(List<TicketEntity> ticketFistStation) {
        this.ticketFistStation = ticketFistStation;
    }

    @OneToMany(mappedBy = "lastStation")
    public List<TicketEntity> getTicketLastStation() {
        return ticketLastStation;
    }

    public void setTicketLastStation(List<TicketEntity> ticketLastStation) {
        this.ticketLastStation = ticketLastStation;
    }

    @LazyCollection(LazyCollectionOption.FALSE)
    @OneToMany(mappedBy = "firstStation")
    public List<ScheduleEntity> getFirstStation() {
        return firstStation;
    }

    public void setFirstStation(List<ScheduleEntity> firstStation) {
        this.firstStation = firstStation;
    }

    @LazyCollection(LazyCollectionOption.FALSE)
    @OneToMany(mappedBy = "lastStation")
    public List<ScheduleEntity> getLastStation() {
        return lastStation;
    }

    public void setLastStation(List<ScheduleEntity> lastStation) {
        this.lastStation = lastStation;
    }

    @LazyCollection(LazyCollectionOption.FALSE)
    @OneToMany(mappedBy = "station")
    public List<DetailedInfBranchEntity> getDetailedInf() {
        return detailedInf;
    }

    public void setDetailedInf(List<DetailedInfBranchEntity> detailedInf) {
        this.detailedInf = detailedInf;
    }

    @Id
    @Column(name = "id_station", nullable = false)
    public int getIdStation() {
        return idStation;
    }

    public void setIdStation(int idStation) {
        this.idStation = idStation;
    }

    @Basic
    @Column(name = "station_name", nullable = true, length = 100)
    public String getStationName() {
        return stationName;
    }

    public void setStationName(String stationName) {
        this.stationName = stationName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        StationEntity that = (StationEntity) o;

        if (idStation != that.idStation) return false;
        if (stationName != null ? !stationName.equals(that.stationName) : that.stationName != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = idStation;
        result = 31 * result + (stationName != null ? stationName.hashCode() : 0);
        return result;
    }
}
