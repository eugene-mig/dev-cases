package com.trustpoint.cases.service;

import com.trustpoint.cases.exception.ResourceNotFoundException;
import com.trustpoint.cases.model.Attachment;
import com.trustpoint.cases.repository.AttachmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class AttachmentService {
    private AttachmentRepository repository;

    @Autowired
    public AttachmentService(AttachmentRepository repository) {
        this.repository = repository;
    }

    public Attachment addAttachment(Attachment attachment, String attachedBy) {
        attachment.setAttachedBy(attachedBy);

        return repository.save(attachment);
    }

    public ResponseEntity<?> deleteAttachment(UUID id) {
        return repository.findById(id).map(attachment -> {
            repository.delete(attachment);
            return ResponseEntity.ok().build();
        }).orElseThrow(() -> new ResourceNotFoundException("attachment not found"));
    }

    public List<Attachment> getAttachments() {
        return repository.findAll();
    }

    public List<Attachment> listByCaseID(UUID caseID) {

//        return repository.findByCaseID(caseID);
        return new ArrayList<>();
    }
}
