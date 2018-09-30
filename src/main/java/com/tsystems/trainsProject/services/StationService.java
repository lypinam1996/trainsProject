package com.tsystems.trainsProject.services;


import com.tsystems.trainsProject.models.StationEntity;

import java.util.List;

public interface StationService {
    List<StationEntity> findAllStations();
    StationEntity findByName(String name);
}
