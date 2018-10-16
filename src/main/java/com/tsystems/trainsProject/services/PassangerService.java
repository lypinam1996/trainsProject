package com.tsystems.trainsProject.services;

import com.tsystems.trainsProject.models.PassangerEntity;

import java.util.List;

public interface PassangerService {
    List<PassangerEntity> findAllPassangers();
    int saveOrUpdate(PassangerEntity passanger);
    PassangerEntity findById(int id);
    boolean checkTheEqualtyPassanger(PassangerEntity passanger);
}
