package com.tsystems.trainsProject.dao;

import com.tsystems.trainsProject.models.StationEntity;

import java.util.List;

public interface StationDAO {
    List<StationEntity> findAllStations();
    StationEntity findByName(String name);
    void saveOrUpdate(StationEntity station);
     StationEntity findById(int id);
}
