package com.tsystems.trainsProject.services;

import com.tsystems.trainsProject.models.UserEntity;

public interface UserService
{
    UserEntity findByLogin(String login);

}
