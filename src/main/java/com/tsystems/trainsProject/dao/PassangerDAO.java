package com.tsystems.trainsProject.dao;

import com.tsystems.trainsProject.models.PassangerEntity;

import java.util.List;

public interface PassangerDAO {
    List<PassangerEntity> findAllPassangers();

    int save(PassangerEntity passanger);

    void update(PassangerEntity passanger);

    PassangerEntity findById(int id);

    void delete(PassangerEntity passanger);
}
