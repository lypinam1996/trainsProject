package com.tsystems.trainsProject.services.impl;
import com.tsystems.trainsProject.dao.impl.StationDAOImpl;
import com.tsystems.trainsProject.models.StationEntity;
import com.tsystems.trainsProject.services.StationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("StationService")
@Transactional
public class StationServiceImpl implements StationService {

    @Autowired
    StationDAOImpl stationDAO;

    @Override
    public List<StationEntity> findAllStations() {
        List<StationEntity> res =stationDAO.findAllStations();
        return res;
    }

    @Override
    public StationEntity findByName(String name) {
        return stationDAO.findByName(name);
    }



}
