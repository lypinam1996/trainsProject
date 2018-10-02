package com.tsystems.trainsProject.services.impl;
import com.tsystems.trainsProject.dao.impl.UserDAOImpl;
import com.tsystems.trainsProject.models.UserEntity;
import com.tsystems.trainsProject.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("UserService")
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    UserDAOImpl userDao;

    @Override
    public List<UserEntity> findAllUsers() {
        List<UserEntity> res =userDao.findAllUsers();
        return res;
    }
}