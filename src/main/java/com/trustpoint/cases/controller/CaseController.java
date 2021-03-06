package com.trustpoint.cases.controller;

import com.trustpoint.cases.common.Utils;
import com.trustpoint.cases.dto.*;
import com.trustpoint.cases.exception.ResourceNotFoundException;
import com.trustpoint.cases.model.Case;
import com.trustpoint.cases.service.CaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RequestMapping("cases")
@RestController
public class CaseController {
	private final CaseService caseService;

	@Autowired
	public CaseController(CaseService caseService) {
		this.caseService = caseService;
	}

	@PostMapping
	public ResponseEntity<HTTPResponse> addCase(@Valid @RequestBody CasePayload casePayload, @RequestHeader Map<String, String> headers) {
		try {
			String user = Utils.getSubjectHeader(headers);
			Case savedCase = caseService.addCase(casePayload, user);
			return ResponseEntity.ok().body(new HTTPResponse(HttpStatus.OK, "", savedCase));
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(new HTTPResponse(HttpStatus.BAD_REQUEST, e.getMessage(), null));
		}
	}

	@PostMapping({"/list"})
	public ResponseEntity<List<Case>> getAllCasesByFilter(@RequestBody(required = false) CaseFilterPayload filter, @RequestHeader Map<String, String> headers) {
		try {
			List<Permission> permissions = Utils.getRequestPermissions(headers);
			BusinessUnit businessUnit = Utils.getRequestBusinessUnit(headers);
			String user = Utils.getSubjectHeader(headers);
			return ResponseEntity.ok(caseService.getCases(filter, user, permissions, businessUnit));
		} catch(Exception e) {
			e.printStackTrace();
			return ResponseEntity.ok(Collections.emptyList());
		}

	}

	@DeleteMapping(path = "{id}")
	public ResponseEntity<?> deleteCase(@PathVariable("id") UUID id) {
		return caseService.deleteCase(id);
	}

	@GetMapping(path = "{id}")
	public Case getPersonByID(@PathVariable("id") UUID id, @NotEmpty @RequestHeader("X-Subject") String owner,
			@RequestHeader(value = "business-units", required = false) String businessUnits) {
		return caseService.getCaseByID(id, owner, businessUnits).orElse(null);
	}

	@PutMapping(path = "{id}")
	public Case updateCase(@PathVariable("id") UUID id, @Valid @NotNull @RequestBody Case update,
			@NotEmpty @RequestHeader("X-Subject") String user) {
		return caseService.updateCase(id, update, user)
				.orElseThrow(() -> new ResourceNotFoundException("Case with id " + id.toString() + " not found"));
	}
}