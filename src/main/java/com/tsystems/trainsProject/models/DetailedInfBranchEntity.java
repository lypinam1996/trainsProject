package com.tsystems.trainsProject.models;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import java.sql.Time;

@Entity
@Table(name = "detailed_inf_branch", schema = "trains", catalog = "")
public class DetailedInfBranchEntity {
    private int idDetailedInfBranch;
    private Integer stationSerialNumber;
    private Time timeFromPrevious;
    private BranchLineEntity branch;
    private StationEntity station;

    @ManyToOne
    @JoinColumn(name = "id_station", referencedColumnName = "id_station")
    public StationEntity getStation() {
        return station;
    }

    public void setStation(StationEntity station) {
        this.station = station;
    }

    @LazyCollection(LazyCollectionOption.FALSE)
    @ManyToOne
    @JoinColumn(name = "id_branch", referencedColumnName = "id_branch_line")//name=fk ref=first key
    public BranchLineEntity getBranch() {
        return branch;
    }

    public void setBranch(BranchLineEntity branch) {
        this.branch = branch;
    }

    @Id
    @Column(name = "id_detailed_inf_branch", nullable = false)
    public int getIdDetailedInfBranch() {
        return idDetailedInfBranch;
    }

    public void setIdDetailedInfBranch(int idDetailedInfBranch) {
        this.idDetailedInfBranch = idDetailedInfBranch;
    }

    @Basic
    @Column(name = "station_serial_number", nullable = true)
    public Integer getStationSerialNumber() {
        return stationSerialNumber;
    }

    public void setStationSerialNumber(Integer stationSerialNumber) {
        this.stationSerialNumber = stationSerialNumber;
    }

    @Basic
    @Column(name = "time_from_previous", nullable = true)
    public Time getTimeFromPrevious() {
        return timeFromPrevious;
    }

    public void setTimeFromPrevious(Time timeFromPrevious) {
        this.timeFromPrevious = timeFromPrevious;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DetailedInfBranchEntity that = (DetailedInfBranchEntity) o;

        if (idDetailedInfBranch != that.idDetailedInfBranch) return false;
        if (stationSerialNumber != null ? !stationSerialNumber.equals(that.stationSerialNumber) : that.stationSerialNumber != null)
            return false;
        if (timeFromPrevious != null ? !timeFromPrevious.equals(that.timeFromPrevious) : that.timeFromPrevious != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = idDetailedInfBranch;
        result = 31 * result + (stationSerialNumber != null ? stationSerialNumber.hashCode() : 0);
        result = 31 * result + (timeFromPrevious != null ? timeFromPrevious.hashCode() : 0);
        return result;
    }

}