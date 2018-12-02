package com.tsystems.trainsProject.dao.impl;

import com.tsystems.trainsProject.dao.TicketDAO;
import com.tsystems.trainsProject.models.TicketEntity;
import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Repository("TicketDAO")
public class TicketDAOImpl extends AbstractDAO<Integer, TicketEntity> implements TicketDAO {

    private static final Logger logger = Logger.getLogger(TicketDAOImpl.class);

    @Override
    public List<TicketEntity> findAllTickets() {
        logger.info("TicketDAOImpl: start to find all tickets");
        Criteria criteria = getSession().createCriteria(TicketEntity.class);
        List<TicketEntity> res = (List<TicketEntity>) criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list();
        logger.info("TicketDAOImpl:all tickets have been found");
        return res;
    }

    @Override
    public void saveOrUpdate(TicketEntity ticketEntity) {
        logger.info("TicketDAOImpl: start to save ticket");
        getSession().save(ticketEntity);
        logger.info("TicketDAOImpl: ticket has been found");
    }

    @Override
    public TicketEntity findById(int id) {
        logger.info("TicketDAOImpl: start to find ticket by id");
        Criteria criteria = getSession().createCriteria(TicketEntity.class);
        criteria.add(Restrictions.eq("idTicket", id));
        logger.info("TicketDAOImpl:ticket has been found");
        return (TicketEntity) criteria.uniqueResult();
    }

    @Override
    public List<TicketEntity> findByDate(Date today)  {
        logger.info("TicketDAOImpl: start to find ticket by date");
        List<TicketEntity> tickets = new ArrayList<>();
        try {
            SimpleDateFormat sm = new SimpleDateFormat("yyyy-MM-dd");
            String strDate = sm.format(today);
            Date dt = sm.parse(strDate);
            Criteria criteria = getSession().createCriteria(TicketEntity.class);
            criteria.add(Restrictions.eq("departureDate", dt));
            tickets = criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list();
            logger.info("TicketDAOImpl:ticket has been found");
        }
        catch (ParseException pe){
            logger.info(pe.getMessage());
        }
        return tickets;
    }


}
