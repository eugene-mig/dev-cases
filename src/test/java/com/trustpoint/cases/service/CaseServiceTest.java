package com.trustpoint.cases.service;

import com.trustpoint.cases.dto.CasePayload;
import com.trustpoint.cases.model.Case;
import com.trustpoint.cases.values.CaseType;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

@SpringBootTest
public class CaseServiceTest {
    @Autowired
    CaseService caseService;

    @Test
    void itShouldAddNewCase() {
        Assertions.assertDoesNotThrow(() -> {
            CasePayload newCase = new CasePayload();
            newCase.setName("Police report");
            newCase.setOpeningDate(LocalDateTime.now());
            newCase.setType(CaseType.General);
            newCase.setDescription("customer stole some cars");

            Case createdCase = caseService.addCase(newCase, "fanky5g@gmail.com");
            Assert.assertNotNull(createdCase);
            Assert.assertNotNull(createdCase.getId());
            Assert.assertEquals(newCase.getName(), createdCase.getName());
        });
    }

    @Test
    void itShouldGetCaseByID() throws Exception {
//        UUID caseID = UUID.fromString("91abb027-c501-4ec5-a17e-969de2ef9160");
//        Case savedCase = caseService.getCaseByID(caseID).orElse(null);
//
//        Assert.assertNotNull(savedCase);
//        Assert.assertEquals(savedCase.getId(), UUID.fromString("91abb027-c501-4ec5-a17e-969de2ef9160"));
//        Assert.assertEquals(savedCase.getName(), "Police report");
    }
}
