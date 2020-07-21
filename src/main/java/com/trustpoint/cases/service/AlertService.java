package com.trustpoint.cases.service;

import com.trustpoint.cases.exception.ResourceNotFoundException;
import com.trustpoint.cases.model.Alert;
import com.trustpoint.cases.repository.AlertRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class AlertService {
    private AlertRepository repository;

    @Autowired
    public AlertService(AlertRepository repository) {
        this.repository = repository;
    }

    public Alert addAlert(Alert alert) {
        return repository.save(alert);
    }

    public ResponseEntity<?> deleteAlert(UUID id) {
        return repository.findById(id).map(alert -> {
            repository.delete(alert);
            return ResponseEntity.ok().build();
        }).orElseThrow(() -> new ResourceNotFoundException("alert not found"));
    }

    public List<Alert> getAlertsByCaseID(UUID caseID) {
//        return repository.listAlertByCaseID(caseID);
        return new ArrayList<>();
    }

    public List<Alert> getAlerts() {
        return repository.findAll();
    }

    public Optional<Alert> findAlertByID(UUID id) {
        return repository.findById(id);
    }
}
