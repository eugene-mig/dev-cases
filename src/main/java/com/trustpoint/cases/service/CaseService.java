package com.trustpoint.cases.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.trustpoint.cases.config.CaseConfiguration;
import com.trustpoint.cases.exception.OpsException;
import com.trustpoint.cases.exception.ResourceNotFoundException;
import com.trustpoint.cases.model.Alert;
import com.trustpoint.cases.model.Case;
import com.trustpoint.cases.model.Note;
import com.trustpoint.cases.repository.AlertRepository;
import com.trustpoint.cases.repository.CaseRepository;
import com.trustpoint.cases.repository.NoteRepository;
import com.trustpoint.cases.values.CaseState;

import io.micrometer.core.instrument.util.StringUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class CaseService {
	private final CaseRepository repository;
	private final AlertRepository alertRepository;
	private final NoteRepository noteRepository;
	private final CaseConfiguration caseConfig;

	private final RestTemplate restTemplate;

	public Case addCase(Case newcase) {
		log.info("Adding case");
		// TODO: run some further case validations
		// TODO: run actions as transaction

		Case createdCase = repository.save(new Case(newcase.getName(), newcase.getType(), newcase.getOpeningDate(),
				newcase.getPriority(), newcase.getDescription(), newcase.getStep(), newcase.getOwner(),
				newcase.getOriginalBusinessUnit(), // at creation business unit will be same as original business unit
				newcase.getOriginalBusinessUnit(), CaseState.OPEN, newcase.getInternalReferenceCode(),
				newcase.getRelatedCaseID(), newcase.getCustomer()));

		// create attached alerts
		if (!newcase.getAlerts().isEmpty()) {
			List<Alert> alerts = newcase.getAlerts();
			for (int i = 0; i < alerts.size(); i++) {
				// how to detect errors???? try - catch?
				Alert alert = alerts.get(i);
				alert.setCaseID(createdCase.getId());
				Alert savedAlert = alertRepository.save(alert);
				alerts.set(i, savedAlert);

				// Link alert to Alerts module
				HttpHeaders headers = new HttpHeaders();
				headers.setContentType(MediaType.APPLICATION_JSON);
				Map<String, String> params = new HashMap<>();

				params.put("caseId", createdCase.getId().toString());
				params.put("actor", createdCase.getOwner());
				HttpEntity<Map<String, String>> request = new HttpEntity<>(params, headers);

				ResponseEntity<String> resp = this.restTemplate.postForEntity(
						caseConfig.getAlertsURI() + "/alerts/link/" + alert.getAlertID(), request, String.class);

				log.trace("Response : {}", resp);

				if (resp.getStatusCode().isError()) {
					throw new OpsException("Failed to link alert");
				}

			}

			createdCase.setAlerts(alerts);
		}

		// create attached notes
		if (!newcase.getNotes().isEmpty()) {
			List<Note> notes = newcase.getNotes();
			for (int i = 0; i < notes.size(); i++) {
				Note note = notes.get(i);
				note.setEnteredBy(createdCase.getOwner());
				note.setCaseID(createdCase.getId());
				Note savedNote = noteRepository.save(note);
				notes.set(i, savedNote);
			}

			createdCase.setNotes(notes);
		}

		return createdCase;
	}

	public List<Case> getCases(String owner, String businessUnit) {
		if (caseConfig.allowAccessIfUnassigned()) {
			return repository.findAll();
		}

		if (StringUtils.isNotBlank(businessUnit)) {
			Long businessUnitID = Long.parseLong(businessUnit, 10);

			if (caseConfig.caseOwnerHasAccess() && StringUtils.isNotBlank(owner)) {
				return repository.findAllByBusinessUnitOrOwner(businessUnitID, owner);
			}

			return repository.findAllByBusinessUnit(businessUnitID);
		}

		if (caseConfig.caseOwnerHasAccess() && StringUtils.isNotBlank(owner)) {
			return repository.findAllByOwner(owner);
		}

		return new ArrayList<>();
	};

	// TODO: apply case access to delete
	public ResponseEntity<?> deleteCase(UUID id) {
		return repository.findById(id).map(found -> {
			repository.delete(found);
			return ResponseEntity.ok().build();
		}).orElseThrow(() -> new ResourceNotFoundException("Case with id " + id.toString() + " not found"));
	}

	// TODO: apply case access to update
	public Optional<Case> updateCase(UUID id, Case update, String user) {
		return repository.findById(id).map(found -> {
			found.setName(update.getName());
			found.setBusinessUnit(update.getBusinessUnit());
			found.setDescription(update.getDescription());
			found.setState(update.getState());
			found.setStep(update.getStep());
			found.setInternalReferenceCode(update.getInternalReferenceCode());
			found.setOwner(update.getOwner());
			found.setPriority(update.getPriority());
			found.setRelatedCaseID(update.getRelatedCaseID());
			found.setType(update.getType());
			found.setOriginalBusinessUnit(update.getOriginalBusinessUnit());
			found.setClosingRemarks(update.getClosingRemarks());

			if (update.getState() == CaseState.CLOSED) {
				found.setClosedBy(user);
				found.setClosingDate(new Date());
			}

			return repository.save(found);
		});
	}

	public Optional<Case> getCaseByID(UUID id, String owner, String businessUnit) {
		if (caseConfig.allowAccessIfUnassigned()) {
			return repository.findById(id);
		}

		if (StringUtils.isNotBlank(owner) && caseConfig.caseOwnerHasAccess()) {
			return repository.findByIdAndOwner(id, owner);
		}

		if (StringUtils.isNotBlank(businessUnit)) {
			Long businessUnitID = Long.parseLong(businessUnit, 10);
			return repository.findByIdAndBusinessUnit(id, businessUnitID);
		}

		return null;
	}
}
