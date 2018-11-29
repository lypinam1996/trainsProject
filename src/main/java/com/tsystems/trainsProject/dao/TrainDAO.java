package com.tsystems.trainsProject.dao;

import com.tsystems.trainsProject.models.TrainEntity;

import java.util.List;

public interface TrainDAO {
    List<TrainEntity> findAllTrains();

    void saveOrUpdate(TrainEntity train);

    TrainEntity findById(int id);
}
