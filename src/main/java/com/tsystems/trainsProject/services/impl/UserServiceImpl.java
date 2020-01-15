package com.tsystems.trainsProject.services.impl;

import com.tsystems.trainsProject.dao.UserDAO;
import com.tsystems.trainsProject.models.UserEntity;
import com.tsystems.trainsProject.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("UserService")
@Transactional
public class UserServiceImpl implements UserService
{

    @Autowired
    UserDAO                       userDao;

    @Override
    public UserEntity findByLogin(String login)
    {
        return userDao.findByLogin(login);
    }

}
