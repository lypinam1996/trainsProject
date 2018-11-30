package com.tsystems.trainsProject.dao.impl;

import com.tsystems.trainsProject.dao.UserDAO;
import com.tsystems.trainsProject.models.UserEntity;
import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository("UserDAO")
public class UserDAOImpl extends AbstractDAO<Integer,UserEntity> implements UserDAO {

    private static final Logger logger = Logger.getLogger(UserDAO.class);
    @Override
    public List<UserEntity> findAllUsers() {
        logger.info("UserDAO: start to find all users");
        Criteria criteria = getSession().createCriteria(UserEntity.class);
        List<UserEntity> res=(List<UserEntity>) criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list();
        logger.info("UserDAO:all users have been found");
        return res;
    }

    @Override
    public UserEntity findByLogin(String login) {
        logger.info("UserDAO: start to find user by login");
        Criteria criteria = getSession().createCriteria(UserEntity.class);
        criteria.add(Restrictions.eq("login", login));
        logger.info("UserDAO:user has been found");
        return (UserEntity) criteria.uniqueResult();
    }

    @Override
    public void saveOrUpdate(UserEntity user) {
        logger.info("UserDAO: start to save user");
        getSession().saveOrUpdate(user);
        logger.info("UserDAO: user has been saved");
    }


}