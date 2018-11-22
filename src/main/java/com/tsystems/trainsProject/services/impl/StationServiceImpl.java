package com.tsystems.trainsProject.services.impl;
import com.tsystems.trainsProject.Events.CustomSpringEvent;
import com.tsystems.trainsProject.dao.impl.StationDAOImpl;
import com.tsystems.trainsProject.models.StationEntity;
import com.tsystems.trainsProject.services.StationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("StationService")
@Transactional
public class StationServiceImpl implements StationService {

    @Autowired
    StationDAOImpl stationDAO;

    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;

    @Override
    public List<StationEntity> findAllStations() {
        CustomSpringEvent customSpringEvent = new CustomSpringEvent(this, "qwe");
        applicationEventPublisher.publishEvent(customSpringEvent);
        List<StationEntity> res =stationDAO.findAllStations();
        return res;
    }

    @Override
    public StationEntity findByName(String name) {
        return stationDAO.findByName(name);
    }

    @Override
    public void saveOrUpdate(StationEntity station) {
        stationDAO.saveOrUpdate(station);
    }

    @Override
    public StationEntity findById(int id) {
        return stationDAO.findById(id);
    }

    @Override
    public void delete(StationEntity stationEntity) {
        stationDAO.delete(stationEntity);
    }

    @Override
    public  String checkUniqueStationName(StationEntity station){
        List<StationEntity> stations = stationDAO.findAllStations();
        stations.remove(stationDAO.findById(station.getIdStation()));
        for (StationEntity stationEntity:stations){
            if(station.getStationName().equals(stationEntity.getStationName())){
                return "*Station name is not unique";
            }
        }
        return "";
    }

}
