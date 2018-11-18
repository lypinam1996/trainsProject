package com.tsystems.trainsProject.DTO;

import javax.persistence.Entity;
import java.util.Date;

public class Search {
    private String firstStation;
    private String lastStation;
    private String departureTimeFrom;
    private String departureTimeTo;

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

    public String getDepartureTimeFrom() {
        return departureTimeFrom;
    }

    public void setDepartureTimeFrom(String departureTimeFrom) {
        this.departureTimeFrom = departureTimeFrom;
    }

    public String getDepartureTimeTo() {
        return departureTimeTo;
    }

    public void setDepartureTimeTo(String departureTimeTo) {
        this.departureTimeTo = departureTimeTo;
    }
}
