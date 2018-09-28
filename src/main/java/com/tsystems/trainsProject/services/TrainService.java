package com.tsystems.trainsProject.services;

import com.tsystems.trainsProject.models.TrainEntity;

import java.util.List;

public interface TrainService {
    List<TrainEntity> findAllTrains();
}
