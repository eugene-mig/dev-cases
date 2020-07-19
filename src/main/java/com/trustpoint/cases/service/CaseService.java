package com.trustpoint.cases.service;

import com.trustpoint.cases.exception.ResourceNotFoundException;
import com.trustpoint.cases.model.Case;
import com.trustpoint.cases.repository.CaseRepository;
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

    @Autowired
    public CaseService(@Qualifier("postgres") CaseRepository repository) {
        this.repository = repository;
    }

    public Case addCase(Case newcase) {
        // TODO: run some further case validations
        return repository.save(new Case(
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
