package com.tsystems.trainsProject.models;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "branch_line", schema = "trains", catalog = "")
public class BranchLineEntity {
    private int idBranchLine;
    private String title;
    private List<DetailedInfBranchEntity> detailedInf;
    private List<ScheduleEntity> schedule;

    @OneToMany(mappedBy = "branch")
    public List<ScheduleEntity> getSchedule() {
        return schedule;
    }

    public void setSchedule(List<ScheduleEntity> schedule) {
        this.schedule = schedule;
    }

    @OneToMany(mappedBy = "branch")
    public List<DetailedInfBranchEntity> getDetailedInf() {
        return detailedInf;
    }

    public void setDetailedInf(List<DetailedInfBranchEntity> detailedInf) {
        this.detailedInf = detailedInf;
    }

    @Id
    @Column(name = "id_branch_line", nullable = false)
    public int getIdBranchLine() {
        return idBranchLine;
    }

    public void setIdBranchLine(int idBranchLine) {
        this.idBranchLine = idBranchLine;
    }

    @Basic
    @Column(name = "title", nullable = true, length = 100)
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BranchLineEntity that = (BranchLineEntity) o;

        if (idBranchLine != that.idBranchLine) return false;
        if (title != null ? !title.equals(that.title) : that.title != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = idBranchLine;
        result = 31 * result + (title != null ? title.hashCode() : 0);
        return result;
    }
}
