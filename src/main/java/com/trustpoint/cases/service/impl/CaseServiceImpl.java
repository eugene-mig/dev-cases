package com.trustpoint.cases.service.impl;

import com.trustpoint.cases.dto.BusinessUnit;
import com.trustpoint.cases.dto.CaseFilterPayload;
import com.trustpoint.cases.dto.Permission;
import com.trustpoint.cases.exception.ResourceNotFoundException;
import com.trustpoint.cases.filters.CaseFilter;
import com.trustpoint.cases.model.Alert;
import com.trustpoint.cases.model.Case;
import com.trustpoint.cases.model.Note;
import com.trustpoint.cases.repository.AlertRepository;
import com.trustpoint.cases.repository.CaseRepository;
import com.trustpoint.cases.repository.NoteRepository;
import com.trustpoint.cases.service.CaseService;
import com.trustpoint.cases.values.CaseState;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class CaseServiceImpl implements CaseService {

    private final CaseRepository repository;
    private final AlertRepository alertRepository;
    private final NoteRepository noteRepository;

    private final RestTemplate restTemplate;

    @Override
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

        for (Alert alert: alerts) {
          alert.setCaseID(createdCase.getId());
          Alert savedAlert = alertRepository.save(alert);
          alerts.add(savedAlert);
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

    @Override
    public List<Case> getCases(CaseFilterPayload filterPayload, String subject, List<Permission> permissions, BusinessUnit businessUnit) {
      CaseFilter filters = new CaseFilter(filterPayload);
      List<Case> cases = repository.findAll(filters);
      return cases;
    }

    // TODO: apply case access to delete
    public ResponseEntity<?> deleteCase(UUID id) {
      return repository.findById(id).map(found -> {
        repository.delete(found);
        return ResponseEntity.ok().build();
      }).orElseThrow(() -> new ResourceNotFoundException("Case with id " + id.toString() + " not found"));
    }

    @Override
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
      return repository.findById(id);
    }
}
