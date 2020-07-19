package com.trustpoint.cases.repository;

import com.trustpoint.cases.model.Attachment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface AttachmentRepository extends JpaRepository<Attachment, UUID> {
    List<Attachment> findByCaseID(UUID caseID);
}
