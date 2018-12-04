package com.tsystems.trainsProject.dao;

import com.tsystems.trainsProject.dto.StationDTO;
import com.tsystems.trainsProject.models.StationEntity;

import java.util.List;

public interface StationDAO {
    List<StationEntity> findAllStations();

    StationEntity findByName(String name);

    void saveOrUpdate(StationEntity station);

    StationEntity findById(int id);

    void delete(StationEntity stationEntity);

    List<StationEntity> getStationsByLetters(String letters);

}
