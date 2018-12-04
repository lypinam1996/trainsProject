package com.tsystems.trainsProject.services.impl;
import com.tsystems.trainsProject.dao.StationDAO;
import com.tsystems.trainsProject.dto.Converter;
import com.tsystems.trainsProject.dto.StationDTO;
import com.tsystems.trainsProject.dao.impl.StationDAOImpl;
import com.tsystems.trainsProject.models.StationEntity;
import com.tsystems.trainsProject.services.StationService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

import javax.management.Query;
import java.util.ArrayList;
import java.util.List;

@Service("StationService")
@Transactional
public class StationServiceImpl implements StationService {

    private static final Logger logger = Logger.getLogger(StationServiceImpl.class);

    @Autowired
    StationDAO stationDAO;

    @Autowired
    Converter converter;

    @Override
    public List<StationEntity> findAllStations() {
        return stationDAO.findAllStations();
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
    public  BindingResult checkUniqueStationName(StationEntity station, BindingResult bindingResult){
        logger.info("StationServiceImpl: check unique station name");
        List<StationEntity> stations = stationDAO.findAllStations();
        stations.remove(stationDAO.findById(station.getIdStation()));
        for (StationEntity stationEntity:stations){
            if(station.getStationName().equals(stationEntity.getStationName())){
                ObjectError objectError = new ObjectError("stationName",
                        "*Station name is not unique");
                bindingResult.addError(objectError);
            }
        }
        logger.info("StationServiceImpl: station has been checked");
        return bindingResult;
    }

    public  List<StationDTO> getStationsByLetters(String letters) {
        List<StationEntity> stationEntities = stationDAO.getStationsByLetters(letters);
        List<StationDTO> stationDTOS = new ArrayList<>();
        for(int i=0;i<stationEntities.size();i++){
            stationDTOS.add(converter.convertStation(stationEntities.get(i)));
        }

        return stationDTOS;
    }
}
