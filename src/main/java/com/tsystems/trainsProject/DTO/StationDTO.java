package com.tsystems.trainsProject.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;

public class StationDTO {
    @JsonProperty("lable")
    private int idStation;
    @JsonProperty("value")
    private String stationName;

    public StationDTO(int idStation, String stationName) {
        this.idStation = idStation;
        this.stationName = stationName;
    }

    public int getIdStation() {
        return idStation;
    }

    public void setIdStation(int idStation) {
        this.idStation = idStation;
    }

    public String getStationName() {
        return stationName;
    }

    public void setStationName(String stationName) {
        this.stationName = stationName;
    }
}
