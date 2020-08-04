package com.trustpoint.cases.repository;

import com.trustpoint.cases.model.Attachment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface AttachmentRepository extends JpaRepository<Attachment, UUID> {
//    List<Attachment> findByCaseID(UUID caseID);
    List<Attachment> findAttachmentsByCaseID(UUID caseID);
}
