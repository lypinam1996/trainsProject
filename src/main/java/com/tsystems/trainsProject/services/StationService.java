package com.tsystems.trainsProject.services;

import com.tsystems.trainsProject.dto.StationDTO;
import com.tsystems.trainsProject.models.StationEntity;
import org.springframework.validation.BindingResult;

import java.util.List;

public interface StationService {
    List<StationEntity> findAllStations();

    StationEntity findByName(String name);

    void saveOrUpdate(StationEntity station);

    StationEntity findById(int id);

    void delete(StationEntity stationEntity);

    BindingResult checkUniqueStationName(StationEntity station,BindingResult bindingResult);

    List<StationDTO> getStationsByLetters(String letters);
}
