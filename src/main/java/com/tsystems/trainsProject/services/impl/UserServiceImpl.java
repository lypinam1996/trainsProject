package com.tsystems.trainsProject.services.impl;
import com.tsystems.trainsProject.dao.impl.RoleDAOImpl;
import com.tsystems.trainsProject.dao.impl.UserDAOImpl;
import com.tsystems.trainsProject.models.RoleEntity;
import com.tsystems.trainsProject.models.UserEntity;
import com.tsystems.trainsProject.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("UserService")
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    UserDAOImpl userDao;

    @Autowired
    RoleDAOImpl roleDAO;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    public List<UserEntity> findAllUsers() {
        List<UserEntity> res =userDao.findAllUsers();
        return res;
    }

    @Override
    public UserEntity findByLogin(String login) {
        return userDao.findByLogin(login);
    }

    @Override
    public void saveOrUpdate(UserEntity user) {
        RoleEntity roleEntity = roleDAO.findByTitle("USER");
        user.setRole(roleEntity);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userDao.saveOrUpdate(user);
    }

}
