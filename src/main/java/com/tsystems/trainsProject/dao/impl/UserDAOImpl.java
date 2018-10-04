package com.tsystems.trainsProject.dao.impl;

import com.tsystems.trainsProject.dao.UserDAO;
import com.tsystems.trainsProject.models.UserEntity;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository("UserDAO")
public class UserDAOImpl extends AbstractDAO<Integer,UserEntity> implements UserDAO {

    @Override
    public List<UserEntity> findAllUsers() {
        Criteria criteria = getSession().createCriteria(UserEntity.class);
        List<UserEntity> res=(List<UserEntity>) criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list();
        return res;
    }

    @Override
    public UserEntity findByLogin(String login) {
        Criteria criteria = getSession().createCriteria(UserEntity.class);
        criteria.add(Restrictions.eq("login", login));
        return (UserEntity) criteria.uniqueResult();
    }

    @Override
    public void saveOrUpdate(UserEntity user) {
        getSession().saveOrUpdate(user);
    }


}