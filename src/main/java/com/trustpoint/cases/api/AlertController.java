package com.trustpoint.cases.api;

import com.trustpoint.cases.model.Alert;
import com.trustpoint.cases.service.AlertService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(path = "alerts")
public class AlertController {
    private AlertService alertService;

    public AlertController(AlertService alertService) {
        this.alertService = alertService;
    }

    @PostMapping
    public Alert addAlert(@NotNull @RequestBody Alert alert) {
        return alertService.addAlert(alert);
    }

    @GetMapping
    public List<Alert> ListAlert(@RequestParam("case_id") UUID caseID) {
        return alertService.getAlertsByCaseID(caseID);
    }

    @DeleteMapping(path = "{alertID}")
    public ResponseEntity<?> deleteAlert(@PathVariable("alertID") UUID alertID) {
        return alertService.deleteAlert(alertID);
    }
}
