package com.tsystems.trainsProject.dao.impl;

import com.tsystems.trainsProject.dao.InfBranchDAO;
import com.tsystems.trainsProject.models.BranchLineEntity;
import com.tsystems.trainsProject.models.DetailedInfBranchEntity;
import com.tsystems.trainsProject.models.StationEntity;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("InfBranchDAO")
public class InfBranchDAOImpl extends AbstractDAO<Integer,DetailedInfBranchEntity> implements InfBranchDAO {

    @Override
    public List<DetailedInfBranchEntity> findAllInfBranch() {
        Criteria criteria = getSession().createCriteria(DetailedInfBranchEntity.class);
        List<DetailedInfBranchEntity> res=(List<DetailedInfBranchEntity>) criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list();
        return res;
    }

    @Override
    public List<DetailedInfBranchEntity> findBranchesByStation(StationEntity firstStation) {
        Criteria criteria = getSession().createCriteria(DetailedInfBranchEntity.class);
        criteria.add(Restrictions.eq("station", firstStation));
        return (List<DetailedInfBranchEntity>) criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list();
    }

    @Override
    public List<DetailedInfBranchEntity> findDetailedInformation(BranchLineEntity branchLineEntity) {
        Criteria criteria = getSession().createCriteria(DetailedInfBranchEntity.class);
        criteria.add(Restrictions.eq("branch", branchLineEntity));
        return (List<DetailedInfBranchEntity>) criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list();
    }

}