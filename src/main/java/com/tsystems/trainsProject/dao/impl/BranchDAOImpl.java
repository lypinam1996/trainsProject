package com.tsystems.trainsProject.dao.impl;

import com.tsystems.trainsProject.dao.BranchDAO;
import com.tsystems.trainsProject.models.BranchLineEntity;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
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

    @Override
    public void saveOrUpdate(BranchLineEntity branch) {
        getSession().save(branch);
    }

    @Override
    public void update(BranchLineEntity branch) {
        getSession().update(branch);
    }


    @Override
    public BranchLineEntity findById(int id) {
        Criteria criteria = getSession().createCriteria(BranchLineEntity.class);
        criteria.add(Restrictions.eq("idBranchLine", id));
        return (BranchLineEntity) criteria.uniqueResult();
    }




}