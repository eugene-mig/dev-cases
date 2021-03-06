package com.trustpoint.cases.controller;

import com.trustpoint.cases.model.Attachment;
import com.trustpoint.cases.service.AttachmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.UUID;

@RestController
public class AttachmentController {
    private AttachmentService attachmentService;

    @Autowired
    public AttachmentController(AttachmentService service) {
        this.attachmentService = service;
    }

    @PostMapping("attachments")
    public Attachment addAttachment(@Valid @NotNull @RequestBody Attachment attachment, @RequestHeader(value = "X-Subject") String attachedBy) {
        return attachmentService.addAttachment(attachment, attachedBy);
    }

    @DeleteMapping("attachments/{id}")
    public ResponseEntity<?> deleteAttachment(@NotEmpty @PathVariable("id") UUID id) {
        return attachmentService.deleteAttachment(id);
    }

    @GetMapping("attachments/{id}")
    public List<Attachment> listByCaseID(@NotEmpty @PathVariable("id") UUID caseID) {
        return attachmentService.listByCaseID(caseID);
    }
}
