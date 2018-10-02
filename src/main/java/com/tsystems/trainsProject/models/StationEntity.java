package com.tsystems.trainsProject.models;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "station", schema = "trains", catalog = "")
public class StationEntity {
    private int idStation;
    private String stationName;
    private List<DetailedInfBranchEntity> detailedInf;
    private List<ScheduleEntity> firstStation;
    private List<ScheduleEntity> lastStation;

    @OneToMany(mappedBy = "firstStation")
    public List<ScheduleEntity> getFirstStation() {
        return firstStation;
    }

    public void setFirstStation(List<ScheduleEntity> firstStation) {
        this.firstStation = firstStation;
    }

    @OneToMany(mappedBy = "lastStation")
    public List<ScheduleEntity> getLastStation() {
        return lastStation;
    }

    public void setLastStation(List<ScheduleEntity> lastStation) {
        this.lastStation = lastStation;
    }

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