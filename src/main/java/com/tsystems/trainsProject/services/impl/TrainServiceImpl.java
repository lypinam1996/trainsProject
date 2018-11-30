package com.tsystems.trainsProject.services.impl;

import com.tsystems.trainsProject.dao.impl.TrainDAOImpl;
import com.tsystems.trainsProject.models.TrainEntity;
import com.tsystems.trainsProject.services.TrainService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

import java.util.List;

@Service("TrainService")
@Transactional
public class TrainServiceImpl implements TrainService {

    private static final Logger logger = Logger.getLogger(TrainServiceImpl.class);

    @Autowired
    TrainDAOImpl trainDAO;

    @Override
    public List<TrainEntity> findAllTrains() {
        List<TrainEntity> res = trainDAO.findAllTrains();
        return res;
    }

    @Override
    public void saveOrUpdate(TrainEntity train) {
        trainDAO.saveOrUpdate(train);
    }

    @Override
    public BindingResult checkUniqueTrainNumber(TrainEntity train, BindingResult bindingResult) {
        logger.info("TrainServiceImpl: check unique train number");
        List<TrainEntity> trains = trainDAO.findAllTrains();
        trains.remove(trainDAO.findById(train.getIdTrain()));
        for (TrainEntity trainEntity : trains) {
            if (train.getNumber().equals(trainEntity.getNumber())) {
                ObjectError objectError = new ObjectError("number",
                        "*Train number is not unique");
                bindingResult.addError(objectError);
            }
        }
        logger.info("TrainServiceImpl: train has been checked");
        return bindingResult;
    }


    @Override
    public TrainEntity findById(int id) {
        return trainDAO.findById(id);
    }

    @Override
    public void delete(TrainEntity train) {
        trainDAO.delete(train);
    }


}
