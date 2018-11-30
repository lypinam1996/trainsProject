package com.tsystems.trainsProject.dao.impl;

import com.tsystems.trainsProject.dao.StationDAO;
import com.tsystems.trainsProject.models.StationEntity;
import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("StationDAO")
public class StationDAOImpl extends AbstractDAO<Integer, StationEntity> implements StationDAO {

    private static final Logger logger = Logger.getLogger(StationDAO.class);

    @Override
    public List<StationEntity> findAllStations() {
        logger.info("StationDAO: start to find all stations");
        Criteria criteria = getSession().createCriteria(StationEntity.class);
        List<StationEntity> res = (List<StationEntity>) criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list();
        logger.info("StationDAO: all stations has been found");
        return res;
    }

    @Override
    public StationEntity findByName(String name) {
        logger.info("StationDAO: start to find station by name");
        Criteria criteria = getSession().createCriteria(StationEntity.class);
        criteria.add(Restrictions.eq("stationName", name));
        logger.info("StationDAO: station has been found");
        return (StationEntity) criteria.uniqueResult();
    }

    @Override
    public void saveOrUpdate(StationEntity station) {
        logger.info("StationDAO: start to save station");
        getSession().saveOrUpdate(station);
        logger.info("StationDAO: station was saved");
    }

    @Override
    public StationEntity findById(int id) {
        logger.info("StationDAO: start to find station by id");
        Criteria criteria = getSession().createCriteria(StationEntity.class);
        criteria.add(Restrictions.eq("idStation", id));
        logger.info("StationDAO: station has been found");
        return (StationEntity) criteria.uniqueResult();
    }

}