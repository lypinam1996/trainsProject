package com.tsystems.trainsProject.services;

import com.tsystems.trainsProject.models.TrainEntity;

import java.util.List;

public interface TrainService {
    List<TrainEntity> findAllTrains();
    void saveOrUpdate(TrainEntity train);
    String checkUniqueTRainNumber(TrainEntity train);
    TrainEntity findById(int id);
    void delete(TrainEntity train);
}
