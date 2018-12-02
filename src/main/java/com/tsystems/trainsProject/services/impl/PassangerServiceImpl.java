package com.tsystems.trainsProject.services.impl;

import com.tsystems.trainsProject.dao.impl.PassangerDAOImpl;
import com.tsystems.trainsProject.models.PassangerEntity;
import com.tsystems.trainsProject.models.TicketEntity;
import com.tsystems.trainsProject.services.PassangerService;
import org.apache.commons.lang.time.DateUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service("PassangerService")
@Transactional
public class PassangerServiceImpl implements PassangerService {

    private static final Logger logger = Logger.getLogger(PassangerServiceImpl.class);

    @Autowired
    PassangerDAOImpl passangerDAO;


    @Override
    public List<PassangerEntity> findAllPassangers() {
        return passangerDAO.findAllPassangers();
    }

    @Override
    public int saveOrUpdate(PassangerEntity passanger) {
        int id = 0;
        if (passanger.getIdPassanger() == 0) {
            id = passangerDAO.save(passanger);
        } else {
            id = passanger.getIdPassanger();
            passangerDAO.update(passanger);
        }
        return id;
    }

    @Override
    public PassangerEntity findById(int id) {
        return passangerDAO.findById(id);
    }
}
