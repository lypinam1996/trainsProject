package com.tsystems.trainsProject.services;


import com.tsystems.trainsProject.models.StationEntity;

import java.util.List;

public interface StationService {
    List<StationEntity> findAllStations();
    StationEntity findByName(String name);
    void saveOrUpdate(StationEntity station);
    StationEntity findById(int id);
    void delete(StationEntity stationEntity);
    String checkUniqueStationName(StationEntity station);
}
