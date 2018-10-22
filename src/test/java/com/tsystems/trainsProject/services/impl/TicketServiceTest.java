package com.tsystems.trainsProject.services.impl;

import com.tsystems.trainsProject.dao.BranchDAO;
import com.tsystems.trainsProject.dao.InfBranchDAO;
import com.tsystems.trainsProject.models.BranchLineEntity;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Collections;

import static org.mockito.ArgumentMatchers.anyObject;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TicketServiceTest {

    @Mock
    BranchDAO branchDAO;

    @Mock
    InfBranchDAO infBranchDAO;

    @InjectMocks
    InfBranchServiceImpl infBranchService;

    @Test
    public void test() {
        BranchLineEntity branchLineEntity = new BranchLineEntity();

        when(branchDAO.findAllBranches()).thenReturn(Collections.<BranchLineEntity>emptyList());
        when(branchDAO.findById(eq(1))).thenReturn(branchLineEntity);


        infBranchService.delete(branchLineEntity, 1);

       // verify(infBranchDAO, times(1)).delete(anyObject());
    }
}
