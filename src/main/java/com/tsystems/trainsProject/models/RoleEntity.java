package com.tsystems.trainsProject.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Basic;

@Entity
@Table(name = "roles", schema = "trains", catalog = "")
public class RoleEntity {
    private int idRole;
    private String title;

    @Id
    @Column(name = "id_role", nullable = false)
    public int getIdRole() {
        return idRole;
    }

    public void setIdRole(int idRole) {
        this.idRole = idRole;
    }

    @Basic
    @Column(name = "title", nullable = true, length = 45)
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

        RoleEntity that = (RoleEntity) o;

        if (idRole != that.idRole) return false;
        if (title != null ? !title.equals(that.title) : that.title != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = idRole;
        result = 31 * result + (title != null ? title.hashCode() : 0);
        return result;
    }
}
