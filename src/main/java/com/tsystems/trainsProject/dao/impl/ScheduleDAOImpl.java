package com.tsystems.trainsProject.dao.impl;

import com.tsystems.trainsProject.dao.ScheduleDAO;
import com.tsystems.trainsProject.models.BranchLineEntity;
import com.tsystems.trainsProject.models.ScheduleEntity;
import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("ScheduleDAO")
public class ScheduleDAOImpl extends AbstractDAO<Integer, ScheduleEntity> implements ScheduleDAO {

    private static final Logger logger = Logger.getLogger(ScheduleDAOImpl.class);

    @Override
    public List<ScheduleEntity> findAllSchedules() {
        logger.info("ScheduleDAOImpl: start to find all schedules");
        Criteria criteria = getSession().createCriteria(ScheduleEntity.class);
        List<ScheduleEntity> res = (List<ScheduleEntity>) criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list();
        logger.info("BranchDao: all schedules have been found");
        return res;
    }

    @Override
    public List<ScheduleEntity> findSchedulesByBranch(BranchLineEntity branchLine) {
        logger.info("ScheduleDAOImpl: start to find schedule by branch");
        Criteria criteria = getSession().createCriteria(ScheduleEntity.class);
        criteria.add(Restrictions.eq("branch", branchLine));
        logger.info("ScheduleDAOImpl: schedule has been found");
        return (List<ScheduleEntity>) criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list();
    }

    @Override
    public int save(ScheduleEntity schedule) {
        logger.info("ScheduleDAOImpl: start to save schedule");
        return (Integer) getSession().save(schedule);
    }

    @Override
    public void update(ScheduleEntity schedule) {
        logger.info("ScheduleDAOImpl: start to update schedule");
        getSession().update(schedule);
        logger.info("ScheduleDAOImpl: schedule was updated");
    }

    @Override
    public int deleteId(ScheduleEntity schedule) {
        logger.info("ScheduleDAOImpl: start to delete schedule");
        getSession().delete(schedule);
        logger.info("ScheduleDAOImpl: schedule was deleted");
        return schedule.getIdSchedule();
    }

    @Override
    public ScheduleEntity findById(int id) {
        logger.info("ScheduleDAOImpl: start to find schedule by id");
        Criteria criteria = getSession().createCriteria(ScheduleEntity.class);
        criteria.add(Restrictions.eq("idSchedule", id));
        logger.info("ScheduleDAOImpl: schedule has been found");
        return (ScheduleEntity) criteria.uniqueResult();
    }
}