package com.tsystems.trainsProject.dao;

import com.tsystems.trainsProject.models.TrainEntity;
import com.tsystems.trainsProject.models.UserEntity;

import java.util.List;

public interface TrainDAO {
    List<TrainEntity> findAllTrains();
}
