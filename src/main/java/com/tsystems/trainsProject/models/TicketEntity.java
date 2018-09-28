package com.tsystems.trainsProject.models;

import javax.persistence.*;

@Entity
@Table(name = "tickets", schema = "trains", catalog = "")
public class TicketEntity {
    private int idTicket;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

        TicketEntity that = (TicketEntity) o;

        if (idTicket != that.idTicket) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return idTicket;
    }
}
