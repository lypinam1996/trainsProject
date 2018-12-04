package com.tsystems.trainsProject.services.impl;
import com.tsystems.trainsProject.dao.TrainDAO;
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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@RunWith(MockitoJUnitRunner.class)
public class TrainServiceTest {

    @InjectMocks
    private TrainServiceImpl service;

    @Mock
    private TrainDAO trainDAO;

    @Test
    public void checkUniqueTrainNumber()
    {
        BindingResult bindingResult=mock(BindingResult.class);
        List<TrainEntity> trains = new ArrayList<>();
        TrainEntity train = new TrainEntity();
        train.setIdTrain(1);
        train.setNumber("A1");
        TrainEntity train2 = new TrainEntity();
        train2.setIdTrain(2);
        train2.setNumber("A2");
        trains.add(train);
        trains.add(train2);
        when(trainDAO.findAllTrains()).thenReturn(Arrays.asList(train,train2));
        TrainEntity newTrain = new TrainEntity();
        newTrain.setIdTrain(3);
        newTrain.setNumber("A2");
        trains.add(newTrain);
        service.checkUniqueTrainNumber(newTrain,bindingResult);
        verify(bindingResult, times(1)).addError(any());

    }
}

