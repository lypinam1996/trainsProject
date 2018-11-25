package com.tsystems.trainsProject.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TicketDto {
    @JsonProperty("idTicket")
    private int idTicket;
    @JsonProperty("login")
    private String login;
    @JsonProperty("departureTime")
    private String departureTime;
    @JsonProperty("arrivalTime")
    private String arrivalTime;
    @JsonProperty("seat")
    private int seat;
    @JsonProperty("name")
    private String name;
    @JsonProperty("surname")
    private String surname;
    @JsonProperty("patronymic")
    private String patronymic;
    @JsonProperty("dateOfBirth")
    private String dateOfBirth;
    @JsonProperty("train")
    private String train;
    @JsonProperty("firstStation")
    private String firstStation;
    @JsonProperty("lastStation")
    private String lastStation;
    @JsonProperty("journeyTime")
    private String journeyTime;
    @JsonProperty("departureDate")
    private String departureDate;

    public TicketDto(int idTicket, String departureTime, String arrivalTime,
                     int seat, String name, String surname, String patronymic,
                     String dateOfBirth, String train, String firstStation,
                     String lastStation, String journeyTime, String departureDate, String login) {
        this.idTicket = idTicket;
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
        this.seat = seat;
        this.name = name;
        this.surname = surname;
        this.patronymic = patronymic;
        this.dateOfBirth = dateOfBirth;
        this.train = train;
        this.firstStation = firstStation;
        this.lastStation = lastStation;
        this.journeyTime = journeyTime;
        this.departureDate = departureDate;
        this.login = login;
    }

    public int getIdTicket() {
        return idTicket;
    }

    public void setIdTicket(int idTicket) {
        this.idTicket = idTicket;
    }

    public String getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(String departureTime) {
        this.departureTime = departureTime;
    }

    public String getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(String arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public int getSeat() {
        return seat;
    }

    public void setSeat(int seat) {
        this.seat = seat;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getTrain() {
        return train;
    }

    public void setTrain(String train) {
        this.train = train;
    }

    public String getFirstStation() {
        return firstStation;
    }

    public void setFirstStation(String firstStation) {
        this.firstStation = firstStation;
    }

    public String getLastStation() {
        return lastStation;
    }

    public void setLastStation(String lastStation) {
        this.lastStation = lastStation;
    }

    public String getJourneyTime() {
        return journeyTime;
    }

    public void setJourneyTime(String journeyTime) {
        this.journeyTime = journeyTime;
    }

    public String getDepartureDate() {
        return departureDate;
    }

    public void setDepartureDate(String departureDate) {
        this.departureDate = departureDate;
    }
}
