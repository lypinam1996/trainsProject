package com.tsystems.trainsProject.services;

import com.tsystems.trainsProject.models.UserEntity;

import java.util.List;

public interface UserService {
    List<UserEntity> findAllUsers();
}