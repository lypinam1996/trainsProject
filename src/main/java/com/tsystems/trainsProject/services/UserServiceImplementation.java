package com.tsystems.trainsProject.services;
import com.tsystems.trainsProject.DAO.UserDAOImplementation;
import com.tsystems.trainsProject.models.UsersEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("UserService")
@Transactional
public class UserServiceImplementation implements UserService {

    @Autowired
    UserDAOImplementation userDao;

    @Override
    public List<UsersEntity> findAllUsers() {
        return userDao.findAllUsers();
    }
}
