package com.tsystems.trainsProject.dao;

import com.tsystems.trainsProject.models.RoleEntity;

public interface RoleDAO {
    RoleEntity findByTitle(String title);
}
