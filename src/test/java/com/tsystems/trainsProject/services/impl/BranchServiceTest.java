package com.tsystems.trainsProject.services.impl;

import com.tsystems.trainsProject.dao.BranchDAO;
import com.tsystems.trainsProject.dao.TrainDAO;
import com.tsystems.trainsProject.models.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.validation.BindingResult;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class BranchServiceTest {

    @InjectMocks
    private BranchServiceImpl branchService;

    @Mock
    private BranchDAO branchDAO;

    @Test
    public void checkTheNecessityOfSaving() throws ParseException {
        BranchLineEntity branch = getBranch1();
        assertEquals(Arrays.asList(branch.getDetailedInf().get(1),
                branch.getDetailedInf().get(2)),
                branchService.checkTheNecessityOfSaving(branch));
    }

    @Test
    public void validation() throws ParseException {
        List<String> result = new ArrayList<>();
        BranchLineEntity branch = getBranch2();
        result.add("*Enter branch title");
        result.add("*Several equal serial numbers was entered");
        result.add("*Some serial numbers was missed");
        result.add("*Several equal stations was selected");
        result.add("*Time can not be equal 00:00");
        assertEquals(result,
                branchService.validation(branch));
    }


    private BranchLineEntity getBranch1() throws ParseException {
        StationEntity station1 = new StationEntity();
        station1.setStationName("1");
        StationEntity station2 = new StationEntity();
        station2.setStationName("2");
        StationEntity station3 = new StationEntity();
        station3.setStationName("3");
        StationEntity station4 = new StationEntity();
        station4.setStationName("4");

        BranchLineEntity branch = new BranchLineEntity();
        branch.setTitle("branch");

        DetailedInfBranchEntity infBranch1 = new DetailedInfBranchEntity();
        infBranch1.setStation(station1);
        infBranch1.setStationSerialNumber(1);
        DateFormat df = new SimpleDateFormat("HH:mm");
        Date date = df.parse("00:09");
        infBranch1.setTimeFromPrevious(date);

        DetailedInfBranchEntity infBranch2 = new DetailedInfBranchEntity();
        infBranch2.setStation(null);
        infBranch2.setStationSerialNumber(null);
        infBranch2.setTimeFromPrevious(null);
        DetailedInfBranchEntity infBranch3 = new DetailedInfBranchEntity();
        infBranch3.setStation(null);
        infBranch3.setStationSerialNumber(null);
        infBranch3.setTimeFromPrevious(null);

        List<DetailedInfBranchEntity> list = new ArrayList<>();
        list.add(infBranch1);
        list.add(infBranch2);
        list.add(infBranch3);

        branch.setDetailedInf(list);
        return branch;
    }

    private BranchLineEntity getBranch2() throws ParseException {
        StationEntity station1 = new StationEntity();
        station1.setStationName("1");
        StationEntity station2 = new StationEntity();
        station2.setStationName("2");

        BranchLineEntity branch = new BranchLineEntity();
        branch.setTitle("");

        DetailedInfBranchEntity infBranch1 = new DetailedInfBranchEntity();
        infBranch1.setStation(station1);
        infBranch1.setStationSerialNumber(1);
        DateFormat df = new SimpleDateFormat("HH:mm");
        Date date = df.parse("00:00");
        Date date1 = df.parse("00:01");
        infBranch1.setTimeFromPrevious(date1);

        DetailedInfBranchEntity infBranch2 = new DetailedInfBranchEntity();
        infBranch2.setStation(station2);
        infBranch2.setStationSerialNumber(1);

        infBranch2.setTimeFromPrevious(date);
        DetailedInfBranchEntity infBranch3 = new DetailedInfBranchEntity();
        infBranch3.setStation(station2);
        infBranch3.setStationSerialNumber(5);
        infBranch3.setTimeFromPrevious(date1);

        List<DetailedInfBranchEntity> list = new ArrayList<>();
        list.add(infBranch1);
        list.add(infBranch2);
        list.add(infBranch3);

        branch.setDetailedInf(list);
        return branch;
    }
}



