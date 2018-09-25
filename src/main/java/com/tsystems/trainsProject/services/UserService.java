package com.tsystems.trainsProject.services;

import com.tsystems.trainsProject.models.UsersEntity;

import java.util.List;

public interface UserService {
    List<UsersEntity> findAllUsers();
}
