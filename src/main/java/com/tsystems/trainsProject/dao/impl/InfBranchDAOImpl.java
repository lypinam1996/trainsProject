package com.tsystems.trainsProject.dao.impl;

import com.tsystems.trainsProject.dao.InfBranchDAO;
import com.tsystems.trainsProject.models.BranchLineEntity;
import com.tsystems.trainsProject.models.DetailedInfBranchEntity;
import com.tsystems.trainsProject.models.ScheduleEntity;
import com.tsystems.trainsProject.models.StationEntity;
import org.hibernate.Criteria;
import org.hibernate.Query;
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
    public DetailedInfBranchEntity findBySerialNumberStationAndSchedule(int serialNumber,
                                                                        BranchLineEntity branch) {
        Criteria criteria = getSession().createCriteria(DetailedInfBranchEntity.class);
        criteria.add(Restrictions.eq("stationSerialNumber", serialNumber));
        criteria.add(Restrictions.eq("branch", branch));
        return (DetailedInfBranchEntity) criteria.uniqueResult();
    }

    @Override
    public List<DetailedInfBranchEntity> findBranchesByStation(StationEntity firstStation) {
        Criteria criteria = getSession().createCriteria(DetailedInfBranchEntity.class);
        criteria.add(Restrictions.eq("station", firstStation));
        return (List<DetailedInfBranchEntity>) criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list();
    }

    @Override
    public int saveOrUpdate(DetailedInfBranchEntity branch) {
        if(branch.getTimeFromPrevious()==null &&
                branch.getStationSerialNumber()==null &&
                branch.getStation()==null){
            return 0;
        }
        else {

            getSession().saveOrUpdate(branch);
            return 1;
        }
    }

    @Override
    public List<DetailedInfBranchEntity> findDetailedInformation(BranchLineEntity branchLineEntity) {
        Criteria criteria = getSession().createCriteria(DetailedInfBranchEntity.class);
        criteria.add(Restrictions.eq("branch", branchLineEntity));
        return (List<DetailedInfBranchEntity>) criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list();
    }


    @Override
    public void delete(DetailedInfBranchEntity branch) {
        int id = branch.getIdDetailedInfBranch();
        Query query = getSession().createSQLQuery("DELETE FROM detailed_inf_branch where id_detailed_inf_branch=:id");
        query.setInteger("id", id);
        query.executeUpdate();
    }


}