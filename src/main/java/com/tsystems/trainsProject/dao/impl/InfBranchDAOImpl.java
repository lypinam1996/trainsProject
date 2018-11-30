package com.tsystems.trainsProject.dao.impl;

import com.tsystems.trainsProject.dao.InfBranchDAO;
import com.tsystems.trainsProject.models.BranchLineEntity;
import com.tsystems.trainsProject.models.DetailedInfBranchEntity;
import com.tsystems.trainsProject.models.StationEntity;
import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("InfBranchDAO")
public class InfBranchDAOImpl extends AbstractDAO<Integer,DetailedInfBranchEntity> implements InfBranchDAO {

    private static final Logger logger = Logger.getLogger(InfBranchDAOImpl.class);

    @Override
    public List<DetailedInfBranchEntity> findAllInfBranch() {
        logger.info("InfBranchDAOImpl: start to find all detailed information about branches");
        Criteria criteria = getSession().createCriteria(DetailedInfBranchEntity.class);
        List<DetailedInfBranchEntity> res=(List<DetailedInfBranchEntity>) criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list();
        logger.info("InfBranchDAOImpl:  all detailed information about branches has been found");
        return res;
    }

    @Override
    public DetailedInfBranchEntity findBySerialNumberStationAndSchedule(int serialNumber,
                                                                        BranchLineEntity branch) {
        logger.info("InfBranchDAOImpl: start to find detailed information about branch by serial number and branch");
        Criteria criteria = getSession().createCriteria(DetailedInfBranchEntity.class);
        criteria.add(Restrictions.eq("stationSerialNumber", serialNumber));
        criteria.add(Restrictions.eq("branch", branch));
        logger.info("InfBranchDAOImpl: detailed information has been found");
        return (DetailedInfBranchEntity) criteria.uniqueResult();
    }

    @Override
    public DetailedInfBranchEntity findById(int id) {
        logger.info("InfBranchDAOImpl: start to find detailed information about branch by id");
        Criteria criteria = getSession().createCriteria(DetailedInfBranchEntity.class);
        criteria.add(Restrictions.eq("idDetailedInfBranch", id));
        logger.info("InfBranchDAOImpl: detailed information has been found");
        return (DetailedInfBranchEntity) criteria.uniqueResult();
    }

    @Override
    public List<DetailedInfBranchEntity> findBranchesByStation(StationEntity firstStation) {
        logger.info("InfBranchDAOImpl: start to find detailed information about branch by station");
        Criteria criteria = getSession().createCriteria(DetailedInfBranchEntity.class);
        criteria.add(Restrictions.eq("station", firstStation));
        logger.info("InfBranchDAOImpl: detailed information has been found");
        return (List<DetailedInfBranchEntity>) criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list();
    }

    @Override
    public void saveOrUpdate(DetailedInfBranchEntity branch) {
        logger.info("InfBranchDAOImpl: start to save detailed information about branch");
        getSession().saveOrUpdate(branch);
        logger.info("InfBranchDAOImpl: detailed information about branch has been found");
    }

    @Override
    public List<DetailedInfBranchEntity> findDetailedInformation(BranchLineEntity branchLineEntity) {
        Criteria criteria = getSession().createCriteria(DetailedInfBranchEntity.class);
        criteria.add(Restrictions.eq("branch", branchLineEntity));
        return (List<DetailedInfBranchEntity>) criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list();
    }


    @Override
    public void delete(DetailedInfBranchEntity branch) {
        logger.info("InfBranchDAOImpl: start to delete detailed information about branch");
        int id = branch.getIdDetailedInfBranch();
        Query query = getSession().createSQLQuery("DELETE FROM detailed_inf_branch where id_detailed_inf_branch=:id");
        query.setInteger("id", id);
        query.executeUpdate();
        logger.info("InfBranchDAOImpl: detailed information about branch has been deleted");
    }


}