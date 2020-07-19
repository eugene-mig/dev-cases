package com.trustpoint.cases.repository;

import com.trustpoint.cases.model.Alert;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface AlertRepository extends JpaRepository<Alert, UUID> {
    public List<Alert> listAlertByCaseID(UUID caseID);
}
