package com.tsystems.trainsProject.dao.impl;

import com.tsystems.trainsProject.dao.TicketDAO;
import com.tsystems.trainsProject.models.TicketEntity;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("TicketDAO")
public class TicketDAOImpl extends AbstractDAO<Integer,TicketEntity> implements TicketDAO{


    @Override
    public List<TicketEntity> findAllTickets() {
        Criteria criteria = getSession().createCriteria(TicketEntity.class);
        List<TicketEntity> res=(List<TicketEntity>) criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list();
        return res;
    }

    @Override
    public TicketEntity findByName(String name) {
        return null;
    }

    @Override
    public void saveOrUpdate(TicketEntity ticketEntity) {
        getSession().saveOrUpdate(ticketEntity);
    }

    @Override
    public TicketEntity findById(int id) {
        Criteria criteria = getSession().createCriteria(TicketEntity.class);
        criteria.add(Restrictions.eq("idTicket", id));
        return (TicketEntity) criteria.uniqueResult();
    }
}
