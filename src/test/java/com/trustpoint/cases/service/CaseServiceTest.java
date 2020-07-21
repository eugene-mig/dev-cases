package com.trustpoint.cases.service;

import com.trustpoint.cases.model.Case;
import com.trustpoint.cases.values.CaseState;
import com.trustpoint.cases.values.CaseType;
import com.trustpoint.cases.values.Priority;
import com.trustpoint.cases.values.Step;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;
import java.util.UUID;

@SpringBootTest
public class CaseServiceTest {
    @Autowired
    CaseService caseService;

    @Test
    void itShouldAddNewCase() throws Exception {
        Case newCase = new Case();
        newCase.setId(UUID.randomUUID());
        newCase.setName("Police report");
        newCase.setOpeningDate(new Date());
        newCase.setType(CaseType.General);
        newCase.setStep(Step.IN_PROCESS);
        newCase.setOwner("fanky5g@gmail.com");
        newCase.setPriority(Priority.HIGH);
        newCase.setState(CaseState.CREATED);
        newCase.setOriginalBusinessUnit(0L);
        newCase.setBusinessUnit(0L);
        newCase.setDescription("customer stole some cars");

        Case createdCase = caseService.addCase(newCase);
        Assert.assertNotNull(createdCase);
        Assert.assertNotNull(createdCase.getId());
        Assert.assertEquals(newCase.getName(), createdCase.getName());
    }

    @Test
    void itShouldGetCaseByID() throws Exception {
        UUID caseID = UUID.fromString("91abb027-c501-4ec5-a17e-969de2ef9160");
        Case savedCase = caseService.getCaseByID(caseID).orElse(null);

        Assert.assertNotNull(savedCase);
        Assert.assertEquals(savedCase.getId(), UUID.fromString("91abb027-c501-4ec5-a17e-969de2ef9160"));
        Assert.assertEquals(savedCase.getName(), "Police report");
    }
}
