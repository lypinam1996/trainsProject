package com.tsystems.trainsProject.dao.impl;

import com.tsystems.trainsProject.dao.TrainDAO;
import com.tsystems.trainsProject.models.TrainEntity;
import org.hibernate.Criteria;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("TrainDAO")
public class TrainDAOImpl extends AbstractDAO<Integer,TrainEntity> implements TrainDAO {

    @Override
    public List<TrainEntity> findAllTrains() {
        Criteria criteria = getSession().createCriteria(TrainEntity.class);
        List<TrainEntity> res=(List<TrainEntity>) criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list();
        return res;
    }

}