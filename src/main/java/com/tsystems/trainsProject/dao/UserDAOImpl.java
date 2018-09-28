package com.tsystems.trainsProject.dao;

import com.tsystems.trainsProject.models.UserEntity;
import org.hibernate.Criteria;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository("UserDAO")
public class UserDAOImpl extends AbstractDAO<Integer,UserEntity> implements UserDAO{

    @Override
    public List<UserEntity> findAllUsers() {
        Criteria criteria = getSession().createCriteria(UserEntity.class);
        List<UserEntity> res=(List<UserEntity>) criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list();
        return res;
    }

}