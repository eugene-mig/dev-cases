package com.trustpoint.cases.repository;

import com.trustpoint.cases.model.Note;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

public interface NoteRepository extends JpaRepository<Note, UUID> {
//    public List<Note> listNotesByCaseID(UUID caseID);
}
