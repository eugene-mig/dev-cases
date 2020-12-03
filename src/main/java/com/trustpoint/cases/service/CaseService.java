package com.trustpoint.cases.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.trustpoint.cases.dto.BusinessUnit;
import com.trustpoint.cases.dto.CaseFilterPayload;
import com.trustpoint.cases.dto.Permission;
import org.springframework.http.ResponseEntity;
import com.trustpoint.cases.model.Case;

public interface CaseService {
	Case addCase(Case newcase);
	List<Case> getCases(CaseFilterPayload filter, String subject, List<Permission> permissions, BusinessUnit businessUnit);
	ResponseEntity<?> deleteCase(UUID id);
	Optional<Case> updateCase(UUID id, Case update, String user);
	Optional<Case> getCaseByID(UUID id, String owner, String businessUnit);
}