package com.trustpoint.cases.service.impl;

import com.trustpoint.cases.dto.*;
import com.trustpoint.cases.exception.ResourceNotFoundException;
import com.trustpoint.cases.filters.CaseFilter;
import com.trustpoint.cases.dto.Alert;
import com.trustpoint.cases.model.Attachment;
import com.trustpoint.cases.model.Case;
import com.trustpoint.cases.model.Note;
import com.trustpoint.cases.repository.CaseRepository;
import com.trustpoint.cases.service.CaseService;
import com.trustpoint.cases.values.CaseState;
import com.trustpoint.cases.values.CaseType;
import com.trustpoint.cases.values.Priority;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class CaseServiceImpl implements CaseService {
  private final CaseRepository repository;

  @Override
  public Case addCase(CasePayload request, String user) throws Exception {
    if (StringUtils.isEmpty(request.getName())) {
      throw new Exception("Case name cannot be empty");
    }

    if (StringUtils.isEmpty(request.getCustomer())) {
      throw new Exception("Customer cannot be empty");
    }

    // ?...should alerts be required for cases????
//    if (request.getAlerts() == null || request.getAlerts().isEmpty()) {
//      throw new Exception("Case must have at least one alert");
//    }

    Case _case = new Case();
    _case.setId(UUID.randomUUID());
    _case.setAlerts(request.getAlerts());
    _case.setCustomer(request.getCustomer());
    _case.setName(request.getName());
    _case.setOwner(user);

    // TODO: get customer business unit
    _case.setBusinessUnit("");

    List<Integer> includedAlertScores = new ArrayList<>(request.getAlerts()).stream().mapToInt(Alert::getScore).boxed().collect(Collectors.toList());
    int maxScore = Collections.max(includedAlertScores);
    _case.setScore(maxScore);
    _case.setPriority(getCasePriority(maxScore));
    _case.setType(request.getType());
    _case.setNotes(request.getNotes());

    return repository.save(_case);
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

  private Priority getCasePriority(int maxScore) {
    if (maxScore > 65) {
      return Priority.HIGH;
    } else if (maxScore > 40) {
      return Priority.MEDIUM;
    }

    return Priority.LOW;
  }

  @Override
  public Case createCaseFromAlert(AlertCaseRequest request) throws Exception {
    Case _case = new Case();
    if (!StringUtils.isEmpty(request.getCaseId())) {
      Optional<Case> existingCase = getCaseByID(UUID.fromString(request.getCaseId()), "", "");
      if (!existingCase.isPresent()) {
        throw new Exception(String.format("Case with id %s not found", request.getCaseId()));
      }

      _case = existingCase.get();
      if (StringUtils.isEmpty(request.getCustomerId())) {
        throw new Exception("Customer ID is required");
      }

      if (request.getCustomerId().equals(_case.getCustomer())) {
        throw new Exception("Existing case customer does not match alert customer");
      }

      // 1. add alert id to case list of alert ids
      Set<Alert> alerts = new HashSet<>(_case.getAlerts());
      alerts.add(new Alert(request.getAlertId()));
      _case.setAlerts(alerts.stream().collect(Collectors.toList()));

      // 2. update case score and priority
      List<Integer> scores = new ArrayList<>();
      scores.add(_case.getScore());
      scores.add(request.getAlertScore());
      Integer highestScore = Collections.max(scores);
      _case.setScore(highestScore);
      _case.setPriority(getCasePriority(highestScore));

      if (request.getAttachments() != null) {
        Set<Attachment> caseAttachments = new HashSet<>(_case.getAttachments());
        for (AlertAttachment alertAttachment: request.getAttachments()) {
          Attachment attachment = new Attachment();
          attachment.setAttachedBy(alertAttachment.getUploadedBy());
          attachment.setCaseID(_case.getId());
          attachment.setDesc(alertAttachment.getComment());
          attachment.setFile(alertAttachment.getFile());
          caseAttachments.add(attachment);
        }

        _case.setAttachments(new ArrayList<>(caseAttachments));
      }

      if (request.getNotes() != null) {
        Set<Note> caseNotes = new HashSet<>(_case.getNotes());
        for (AlertNote alertNote: request.getNotes()) {
          Note note = new Note();
          note.setBody(alertNote.getBody());
          note.setEnteredBy(alertNote.getAddedBy());
          note.setCaseID(_case.getId());
          caseNotes.add(note);
        }

        _case.setNotes(new ArrayList<>(caseNotes));
      }

    } else {
      _case.setId(UUID.randomUUID());
      _case.setAlerts(List.of(new Alert(request.getAlertId())));
      _case.setCustomer(request.getCustomerId());
      _case.setName(request.getTitle());
      _case.setOwner(request.getOwner());
      _case.setBusinessUnit(request.getBusinessUnit());
      _case.setScore(request.getAlertScore());
      _case.setPriority(getCasePriority(request.getAlertScore()));
      _case.setType(CaseType.valueOf(request.getCaseType()));

      Set<Note> caseNotes = new HashSet<>();
      if (request.getNotes() != null) {
        for (AlertNote alertNote: request.getNotes()) {
          Note note = new Note();
          note.setBody(alertNote.getBody());
          note.setEnteredBy(alertNote.getAddedBy());
          note.setCaseID(_case.getId());
          caseNotes.add(note);
        }
      }

      _case.setNotes(new ArrayList<>(caseNotes));

      if (request.getAttachments() != null) {
        Set<Attachment> caseAttachments = new HashSet<>();
        for (AlertAttachment alertAttachment: request.getAttachments()) {
          Attachment attachment = new Attachment();
          attachment.setAttachedBy(alertAttachment.getUploadedBy());
          attachment.setCaseID(_case.getId());
          attachment.setDesc(alertAttachment.getComment());
          attachment.setFile(alertAttachment.getFile());
          caseAttachments.add(attachment);
        }

        _case.setAttachments(new ArrayList<>(caseAttachments));
      }
    }

    // save case
    return repository.save(_case);
  }

  @Override
  public Case addAlert(Alert alert) throws Exception {
    return null;
  }

  @Override
  public Optional<Case> deleteAlert(String alertId) {
    return Optional.empty();
  }
}
