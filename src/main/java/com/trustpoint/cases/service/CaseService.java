package com.trustpoint.cases.service;

import com.trustpoint.cases.exception.ResourceNotFoundException;
import com.trustpoint.cases.model.Alert;
import com.trustpoint.cases.model.Case;
import com.trustpoint.cases.model.Note;
import com.trustpoint.cases.repository.AlertRepository;
import com.trustpoint.cases.repository.CaseRepository;
import com.trustpoint.cases.repository.NoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class CaseService {
    private final CaseRepository repository;
    private final AlertRepository alertRepository;
    private final NoteRepository noteRepository;

    @Autowired
    public CaseService(@Qualifier("postgres") CaseRepository repository, AlertRepository alertRepository, NoteRepository noteRepository) {
        this.repository = repository;
        this.alertRepository = alertRepository;
        this.noteRepository = noteRepository;
    }

    public Case addCase(Case newcase) {
        // TODO: run some further case validations
        // TODO: run actions as transaction

        Case createdCase = repository.save(new Case(
                newcase.getName(),
                newcase.getType(),
                newcase.getOpeningDate(),
                newcase.getPriority(),
                newcase.getDescription(),
                newcase.getStep(),
                newcase.getOwner(),
                newcase.getBusinessUnit(),
                newcase.getOriginalBusinessUnit(),
                newcase.getState(),
                newcase.getInternalReferenceCode(),
                newcase.getRelatedCaseID(),
                newcase.getCustomer()
        ));

        // create attached alerts
        if (!newcase.getAlerts().isEmpty()) {
            List<Alert> alerts = newcase.getAlerts();
            for (int i = 0; i < alerts.size(); i++) {
                // how to detect errors???? try - catch?
                Alert alert = alerts.get(i);
                alert.setCaseID(createdCase.getId());
                Alert savedAlert = alertRepository.save(alert);
                alerts.set(i, savedAlert);
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

    public List<Case> getCases() {
      return repository.findAll();
    };

    public ResponseEntity<?> deleteCase (UUID id) {
        return repository.findById(id).map(found -> {
            repository.delete(found);
            return ResponseEntity.ok().build();
        }).orElseThrow(() -> new ResourceNotFoundException("Case with id " + id.toString() + " not found"));
    }

    public Optional<Case> updateCase(UUID id, Case update) {
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
            return repository.save(found);
        });
    }

    public Optional<Case> getCaseByID(UUID id) {
        return repository.findById(id);
    }
}
