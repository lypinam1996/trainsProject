package com.tsystems.trainsProject.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "tickets", schema = "trains", catalog = "")
public class TicketsEntity {
    private int idTicket;

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

        TicketsEntity that = (TicketsEntity) o;

        if (idTicket != that.idTicket) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return idTicket;
    }
}
