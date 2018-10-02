package com.tsystems.trainsProject.services.impl;
import com.tsystems.trainsProject.dao.impl.TrainDAOImpl;
import com.tsystems.trainsProject.models.TrainEntity;
import com.tsystems.trainsProject.services.TrainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("TrainService")
@Transactional
public class TrainServiceImpl implements TrainService {

    @Autowired
    TrainDAOImpl trainDAO;

    @Override
    public List<TrainEntity> findAllTrains() {
        List<TrainEntity> res = trainDAO.findAllTrains();
        return res;
    }
}