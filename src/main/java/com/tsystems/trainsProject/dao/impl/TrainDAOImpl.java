package com.tsystems.trainsProject.dao.impl;

import com.tsystems.trainsProject.dao.TrainDAO;
import com.tsystems.trainsProject.models.TrainEntity;
import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("TrainDAO")
public class TrainDAOImpl extends AbstractDAO<Integer, TrainEntity> implements TrainDAO {

    private static final Logger logger = Logger.getLogger(TrainDAOImpl.class);

    @Override
    public List<TrainEntity> findAllTrains() {
        logger.info("TrainDAOImpl: start to find all trains");
        Criteria criteria = getSession().createCriteria(TrainEntity.class);
        List<TrainEntity> res = (List<TrainEntity>) criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list();
        logger.info("TrainDAOImpl:all trains have been found");
        return res;
    }

    @Override
    public void saveOrUpdate(TrainEntity train) {
        logger.info("TrainDAOImpl: start to save train");
        getSession().saveOrUpdate(train);
        logger.info("TrainDAOImpl: train has been saved");
    }

    @Override
    public TrainEntity findById(int id) {
        logger.info("TrainDAOImpl: start to find train by id");
        Criteria criteria = getSession().createCriteria(TrainEntity.class);
        criteria.add(Restrictions.eq("idTrain", id));
        logger.info("TrainDAOImpl: train has been found");
        return (TrainEntity) criteria.uniqueResult();
    }

}