package com.tsystems.trainsProject.dao.impl;

import com.tsystems.trainsProject.dao.PassangerDAO;
import com.tsystems.trainsProject.models.PassangerEntity;
import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("PassangerDAO")
public class PassangerDAOImpl extends AbstractDAO<Integer, PassangerEntity> implements PassangerDAO {

    private static final Logger logger = Logger.getLogger(PassangerDAOImpl.class);

    @Override
    public List<PassangerEntity> findAllPassangers() {
        logger.info("PassangerDAOImpl: start to find all pasangers");
        Criteria criteria = getSession().createCriteria(PassangerEntity.class);
        List<PassangerEntity> res = (List<PassangerEntity>) criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list();
        logger.info("PassangerDAOImpl: pasangers has been found");
        return res;
    }

    @Override
    public int save(PassangerEntity passanger) {
        logger.info("PassangerDAOImpl: start to save passanger");
        return (Integer)getSession().save(passanger);
    }

    @Override
    public void update(PassangerEntity passanger) {
        logger.info("PassangerDAOImpl: start to update passanger");
        getSession().update(passanger);
        logger.info("PassangerDAOImpl: passanger has been updated");
    }

    @Override
    public PassangerEntity findById(int id) {
        logger.info("PassangerDAOImpl: start to find pasanger by id");
        Criteria criteria = getSession().createCriteria(PassangerEntity.class);
        criteria.add(Restrictions.eq("idPassanger", id));
        logger.info("PassangerDAOImpl: pasanger has been found");
        return (PassangerEntity) criteria.uniqueResult();
    }

    @Override
    public void delete(PassangerEntity passanger) {
        logger.info("PassangerDAOImpl: start to delete passanger");
        getSession().delete(passanger);
        logger.info("PassangerDAOImpl: passanger has been deleted");
    }
}
