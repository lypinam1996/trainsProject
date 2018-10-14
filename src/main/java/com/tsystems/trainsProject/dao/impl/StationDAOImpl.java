package com.tsystems.trainsProject.dao.impl;

import com.tsystems.trainsProject.dao.StationDAO;
import com.tsystems.trainsProject.models.StationEntity;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("StationDAO")
public class StationDAOImpl extends AbstractDAO<Integer,StationEntity> implements StationDAO {

    @Override
    public List<StationEntity> findAllStations() {
        Criteria criteria = getSession().createCriteria(StationEntity.class);
        List<StationEntity> res=(List<StationEntity>) criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list();
        return res;
    }

    @Override
    public StationEntity findByName(String name) {
        Criteria criteria = getSession().createCriteria(StationEntity.class);
        criteria.add(Restrictions.eq("stationName", name));
        return (StationEntity) criteria.uniqueResult();
    }

    @Override
    public void saveOrUpdate(StationEntity station) {
        getSession().saveOrUpdate(station);
    }

    @Override
    public StationEntity findById(int id) {
        Criteria criteria = getSession().createCriteria(StationEntity.class);
        criteria.add(Restrictions.eq("idStation", id));
        return (StationEntity) criteria.uniqueResult();
    }

}