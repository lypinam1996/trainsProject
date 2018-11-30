package com.tsystems.trainsProject.dao.impl;

import com.tsystems.trainsProject.dao.PassangerDAO;
import com.tsystems.trainsProject.models.PassangerEntity;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("PassangerDAO")
public class PassangerDAOImpl extends AbstractDAO<Integer, PassangerEntity> implements PassangerDAO {
    @Override
    public List<PassangerEntity> findAllPassangers() {
        Criteria criteria = getSession().createCriteria(PassangerEntity.class);
        List<PassangerEntity> res = (List<PassangerEntity>) criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list();
        return res;
    }

    @Override
    public int save(PassangerEntity passanger) {
        return (Integer)getSession().save(passanger);
    }

    @Override
    public void update(PassangerEntity passanger) {
         getSession().update(passanger);
    }

    @Override
    public PassangerEntity findById(int id) {
        Criteria criteria = getSession().createCriteria(PassangerEntity.class);
        criteria.add(Restrictions.eq("idPassanger", id));
        return (PassangerEntity) criteria.uniqueResult();
    }

    @Override
    public void delete(PassangerEntity passanger) {
        getSession().delete(passanger);
    }
}
