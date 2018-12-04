package com.tsystems.trainsProject.dao.impl;

import com.tsystems.trainsProject.dao.BranchDAO;
import com.tsystems.trainsProject.models.BranchLineEntity;
import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("BranchDAO")
public class BranchDAOImpl extends AbstractDAO<Integer, BranchLineEntity> implements BranchDAO {

    private static final Logger logger = Logger.getLogger(BranchDAOImpl.class);

    @Override
    public List<BranchLineEntity> findAllBranches() {
        logger.info("BranchDao: start to find all branches");
        Criteria criteria = getSession().createCriteria(BranchLineEntity.class);
        List<BranchLineEntity> res = (List<BranchLineEntity>) criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list();
        logger.info("BranchDao: all branches has been found");
        return res;
    }

    @Override
    public void saveOrUpdate(BranchLineEntity branch) {
        logger.info("BranchDao: start to save branch");
        getSession().saveOrUpdate(branch);
        logger.info("BranchDao: branch has been saved");
    }

    @Override
    public void update(BranchLineEntity branch) {
        logger.info("BranchDao: start to update branch");
        getSession().update(branch);
        logger.info("BranchDao: branch has been updated");
    }


    @Override
    public BranchLineEntity findById(int id) {
        logger.info("BranchDao: start to find branch by id");
        Criteria criteria = getSession().createCriteria(BranchLineEntity.class);
        criteria.add(Restrictions.eq("idBranchLine", id));
        logger.info("BranchDao: branch has been found");
        return (BranchLineEntity) criteria.uniqueResult();
    }


}