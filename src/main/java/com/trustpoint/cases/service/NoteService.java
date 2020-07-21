package com.trustpoint.cases.service;

import com.trustpoint.cases.exception.ResourceNotFoundException;
import com.trustpoint.cases.model.Note;
import com.trustpoint.cases.repository.NoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class NoteService {
    private NoteRepository repository;

    @Autowired
    public NoteService(NoteRepository repository) {
        this.repository = repository;
    }

    public Note addNote(Note note, String enteredBy) {
        note.setEnteredBy(enteredBy);
        return repository.save(note);
    }

    public List<Note> getNotesByCaseID(UUID caseID) {
//        return repository.listNotesByCaseID(caseID);
        return new ArrayList<>();
    }

    public ResponseEntity<?> deleteNote(UUID id) {
        return repository.findById(id).map(note -> {
            repository.delete(note);
            return ResponseEntity.ok().build();
        }).orElseThrow(() -> new ResourceNotFoundException("Note not found"));
    }

    public List<Note> getNotes() {
        return repository.findAll();
    }

    public Optional<Note> findNoteByID(UUID id) {
        return repository.findById(id);
    }
}
