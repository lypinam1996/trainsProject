package com.tsystems.trainsProject.services.impl;
import com.tsystems.trainsProject.dao.impl.PassangerDAOImpl;
import com.tsystems.trainsProject.models.PassangerEntity;
import com.tsystems.trainsProject.services.PassangerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("PassangerService")
@Transactional
public class PassangerServiceImpl implements PassangerService {

    @Autowired
    PassangerDAOImpl passangerDAO;


    @Override
    public List<PassangerEntity> findAllPassangers() {
        return passangerDAO.findAllPassangers();
    }

    @Override
    public int saveOrUpdate(PassangerEntity passanger) {
        int id=0;
        if(passangerDAO.findAllPassangers().size()!=0) {
             id = passangerDAO.findAllPassangers().get(passangerDAO.findAllPassangers().size() - 1).getIdPassanger();
        }
        passanger.setIdPassanger(id+1);
        passangerDAO.saveOrUpdate(passanger);
        return id+1;
    }
    @Override
    public boolean checkTheEqualtyPassanger(PassangerEntity passanger, List<PassangerEntity> allPassangers){
        boolean ok = true;
        if(!allPassangers.isEmpty()) {
            int i = 0;
            while (i < allPassangers.size() && ok) {
                if (allPassangers.get(i).getDateOfBirth().compareTo(passanger.getDateOfBirth()) == 0 &&
                        allPassangers.get(i).getName().equals(passanger.getName()) &&
                        allPassangers.get(i).getSurname().equals(passanger.getSurname()) &&
                        allPassangers.get(i).getPatronymic().equals(passanger.getPatronymic())) {
                    ok = false;
                } else {
                    i++;
                }
            }
        }
        return ok;
    }

    @Override
    public PassangerEntity findById(int id) {
        return passangerDAO.findById(id);
    }
}
