package com.trustpoint.cases.controller;

import com.trustpoint.cases.dto.Alert;
import com.trustpoint.cases.model.Case;
import com.trustpoint.cases.service.CaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;

@RestController
@RequestMapping(path = "alerts")
@RequiredArgsConstructor
public class AlertController {
    private final CaseService caseService;

    @PostMapping
    public ResponseEntity<Case> addAlert(@NotNull @RequestBody Alert alert) {
        try {
            return ResponseEntity.ok(caseService.addAlert(alert));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping(path = "{alertID}")
    public ResponseEntity<?> deleteAlert(@PathVariable("alertID") String alertID) {
        try {
            return ResponseEntity.ok(caseService.deleteAlert(alertID));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/tocase")
    public ResponseEntity<?> createCaseFromAlert() {
        return ResponseEntity.ok().build();
    }
}
