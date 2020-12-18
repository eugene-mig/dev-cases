package com.trustpoint.cases.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.trustpoint.cases.dto.*;
import org.springframework.http.ResponseEntity;
import com.trustpoint.cases.model.Case;

public interface CaseService {
	Case addCase(CasePayload newcase, String user) throws Exception;
	List<Case> getCases(CaseFilterPayload filter, String subject, List<Permission> permissions, BusinessUnit businessUnit);
	ResponseEntity<?> deleteCase(UUID id);
	Optional<Case> updateCase(UUID id, Case update, String user);
	Optional<Case> getCaseByID(UUID id, String owner, String businessUnit);
	Case createCaseFromAlert(AlertCaseRequest request) throws Exception;
	Case addAlert(Alert alert) throws Exception;
	Optional<Case> deleteAlert(String alertId);
}