package com.tsystems.trainsProject.models;

import javax.persistence.*;

@Entity
@Table(name = "trains", schema = "trains", catalog = "")
public class TrainEntity {
    private int idTrain;
    private String number;
    private Integer numberOfSeats;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_train", nullable = false)
    public int getIdTrain() {
        return idTrain;
    }

    public void setIdTrain(int idTrain) {
        this.idTrain = idTrain;
    }

    @Basic
    @Column(name = "number", nullable = true, length = 45)
    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    @Basic
    @Column(name = "number_of_seats", nullable = true)
    public Integer getNumberOfSeats() {
        return numberOfSeats;
    }

    public void setNumberOfSeats(Integer numberOfSeats) {
        this.numberOfSeats = numberOfSeats;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TrainEntity that = (TrainEntity) o;

        if (idTrain != that.idTrain) return false;
        if (number != null ? !number.equals(that.number) : that.number != null) return false;
        if (numberOfSeats != null ? !numberOfSeats.equals(that.numberOfSeats) : that.numberOfSeats != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = idTrain;
        result = 31 * result + (number != null ? number.hashCode() : 0);
        result = 31 * result + (numberOfSeats != null ? numberOfSeats.hashCode() : 0);
        return result;
    }
}
