package com.tsystems.trainsProject.models;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "passanger", schema = "trains", catalog = "")
public class PassangerEntity {
    private int idPassanger;
    private String name;
    private String surname;
    private String patronymic;
    private Timestamp dateOfBirth;

    @Id
    @Column(name = "id_passanger", nullable = false)
    public int getIdPassanger() {
        return idPassanger;
    }

    public void setIdPassanger(int idPassanger) {
        this.idPassanger = idPassanger;
    }

    @Basic
    @Column(name = "name", nullable = true, length = 100)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "surname", nullable = true, length = 100)
    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    @Basic
    @Column(name = "patronymic", nullable = true, length = 100)
    public String getPatronymic() {
        return patronymic;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

    @Basic
    @Column(name = "date_of_birth", nullable = true)
    public Timestamp getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Timestamp dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PassangerEntity that = (PassangerEntity) o;

        if (idPassanger != that.idPassanger) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (surname != null ? !surname.equals(that.surname) : that.surname != null) return false;
        if (patronymic != null ? !patronymic.equals(that.patronymic) : that.patronymic != null) return false;
        if (dateOfBirth != null ? !dateOfBirth.equals(that.dateOfBirth) : that.dateOfBirth != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = idPassanger;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (surname != null ? surname.hashCode() : 0);
        result = 31 * result + (patronymic != null ? patronymic.hashCode() : 0);
        result = 31 * result + (dateOfBirth != null ? dateOfBirth.hashCode() : 0);
        return result;
    }
}
