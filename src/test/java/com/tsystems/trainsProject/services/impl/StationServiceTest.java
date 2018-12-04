package com.tsystems.trainsProject.services.impl;

import com.tsystems.trainsProject.dao.StationDAO;
import com.tsystems.trainsProject.models.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.validation.BindingResult;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@RunWith(MockitoJUnitRunner.class)
public class StationServiceTest {

    @InjectMocks
    StationServiceImpl stationService;

    @Mock
    StationDAO stationDAO;

    @Test
    public void checkUniqueStationName()
    {
        BindingResult bindingResult=mock(BindingResult.class);
        List<StationEntity> stations = new ArrayList<>();
        StationEntity station1=new StationEntity();
        station1.setStationName("a");
        stations.add(station1);
        when(stationDAO.findAllStations()).thenReturn(Arrays.asList(station1));
        StationEntity station2=new StationEntity();
        station2.setStationName("a");
        stationService.checkUniqueStationName(station2,bindingResult);
        verify(bindingResult, times(1)).addError(any());

    }
}

