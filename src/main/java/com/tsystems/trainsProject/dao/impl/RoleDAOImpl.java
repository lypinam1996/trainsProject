package com.tsystems.trainsProject.dao.impl;

import com.tsystems.trainsProject.dao.RoleDAO;
import com.tsystems.trainsProject.models.RoleEntity;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;


@Repository("RoleDAO")
public class RoleDAOImpl extends AbstractDAO<Integer,RoleEntity> implements RoleDAO {

    @Override
    public RoleEntity findByTitle(String login) {
        Criteria criteria = getSession().createCriteria(RoleEntity.class);
        criteria.add(Restrictions.eq("title", login));
        return (RoleEntity) criteria.uniqueResult();
    }
}