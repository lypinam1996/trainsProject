package com.tsystems.trainsProject.services;

import com.tsystems.trainsProject.models.TrainEntity;
import org.springframework.validation.BindingResult;

import java.util.List;

public interface TrainService {
    List<TrainEntity> findAllTrains();

    void saveOrUpdate(TrainEntity train);

    BindingResult checkUniqueTrainNumber(TrainEntity train, BindingResult bindingResult);

    TrainEntity findById(int id);

    void delete(TrainEntity train);
}
