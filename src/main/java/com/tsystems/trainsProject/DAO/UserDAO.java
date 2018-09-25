package com.tsystems.trainsProject.DAO;

import com.tsystems.trainsProject.models.UsersEntity;

import java.util.List;

public interface UserDAO {
    List<UsersEntity> findAllUsers();
}