package com.tsystems.trainsProject.services.impl;
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

import java.util.ArrayList;
import java.util.List;

@Service("StationService")
@Transactional
public class StationServiceImpl implements StationService {

    private static final Logger logger = Logger.getLogger(StationServiceImpl.class);

    @Autowired
    StationDAOImpl stationDAO;

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
        logger.info("StationServiceImpl: start getting stations by letters");
        List<StationDTO> stationsDTO = new ArrayList<StationDTO>();
        List<StationEntity> stations = findAllStations();
        letters=letters.substring(0,1).toUpperCase()+letters.substring(1,letters.length());
        Converter converter = new Converter();
        for (int i = 0; i < stations.size(); i++) {
            int j=0;
            boolean ok=true;
            while (j<letters.length() && ok) {
                if (stations.get(i).getStationName().substring(j, j + 1).equals(letters.substring(j, j + 1))) {
                    j++;
                }
                else {
                    ok=false;
                }
            }
            if( ok){
                stationsDTO.add(converter.convertStation(stations.get(i)));
            }
        }
        logger.info("StationServiceImpl: station has been found");
        return stationsDTO;
    }
}
