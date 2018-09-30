package com.tsystems.trainsProject.dao.impl;

import com.tsystems.trainsProject.dao.BranchDAO;
import com.tsystems.trainsProject.models.BranchLineEntity;
import org.hibernate.Criteria;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("BranchDAO")
public class BranchDAOImpl extends AbstractDAO<Integer,BranchLineEntity> implements BranchDAO {

    @Override
    public List<BranchLineEntity> findAllBranches() {
        Criteria criteria = getSession().createCriteria(BranchLineEntity.class);
        List<BranchLineEntity> res=(List<BranchLineEntity>) criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list();
        return res;
    }

}