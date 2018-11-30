package com.tsystems.trainsProject.services.impl;

import com.tsystems.trainsProject.dao.impl.PassangerDAOImpl;
import com.tsystems.trainsProject.models.PassangerEntity;
import com.tsystems.trainsProject.services.PassangerService;
import org.apache.commons.lang.time.DateUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    public boolean checkTheEqualtyPassanger(PassangerEntity passanger, List<PassangerEntity> allPassangers) {
        logger.info("PassangerServiceImpl: check the equalty of passanger");
        boolean ok = true;
        if (!allPassangers.isEmpty()) {
            int i = 0;
            while (i < allPassangers.size() && ok) {
                if (allPassangers.get(i).getDateOfBirth() == null && passanger.getDateOfBirth() == null) {
                    if (allPassangers.get(i).getName().equals(passanger.getName()) &&
                            allPassangers.get(i).getSurname().equals(passanger.getSurname()) &&
                            allPassangers.get(i).getPatronymic().equals(passanger.getPatronymic())) {
                        ok = false;
                    } else {
                        i++;
                    }
                } else {
                    if (allPassangers.get(i).getDateOfBirth() != null && passanger.getDateOfBirth() != null) {
                        if (DateUtils.isSameInstant(allPassangers.get(i).getDateOfBirth(), passanger.getDateOfBirth()) &&
                                allPassangers.get(i).getName().equals(passanger.getName()) &&
                                allPassangers.get(i).getSurname().equals(passanger.getSurname()) &&
                                allPassangers.get(i).getPatronymic().equals(passanger.getPatronymic())) {
                            ok = false;
                        } else {
                            i++;
                        }
                    } else {
                        i++;
                    }
                }
            }
        }
        logger.info("PassangerServiceImpl: branch has been checked");
        return ok;
    }

    @Override
    public PassangerEntity findById(int id) {
        return passangerDAO.findById(id);
    }
}
