package com.tsystems.trainsProject.dao.impl;

import com.tsystems.trainsProject.dao.ScheduleDAO;
import com.tsystems.trainsProject.models.BranchLineEntity;
import com.tsystems.trainsProject.models.ScheduleEntity;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("ScheduleDAO")
public class ScheduleDAOImpl extends AbstractDAO<Integer,ScheduleEntity> implements ScheduleDAO {

    @Override
    public List<ScheduleEntity> findAllSchedules() {
        Criteria criteria = getSession().createCriteria(ScheduleEntity.class);
        List<ScheduleEntity> res=(List<ScheduleEntity>) criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list();
        return res;
    }

    @Override
    public List<ScheduleEntity> findSchedulesByBranch(BranchLineEntity branchLine) {
        Criteria criteria = getSession().createCriteria(ScheduleEntity.class);
        criteria.add(Restrictions.eq("branch", branchLine));
        return (List<ScheduleEntity>) criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list();
    }

}