package com.tsystems.trainsProject.dao;

import com.tsystems.trainsProject.models.PassangerEntity;

import java.util.List;

public interface PassangerDAO {
    List<PassangerEntity> findAllPassangers();
    void saveOrUpdate(PassangerEntity passanger);
    PassangerEntity findById(int id);
}
