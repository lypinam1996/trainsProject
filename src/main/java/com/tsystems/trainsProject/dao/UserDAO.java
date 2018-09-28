package com.tsystems.trainsProject.dao;

import com.tsystems.trainsProject.models.UserEntity;
import java.util.List;
public interface UserDAO {
    List<UserEntity> findAllUsers();
}
