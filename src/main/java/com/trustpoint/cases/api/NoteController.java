package com.trustpoint.cases.api;

import com.trustpoint.cases.exception.ResourceNotFoundException;
import com.trustpoint.cases.model.Note;
import com.trustpoint.cases.service.NoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.UUID;

@RestController
public class NoteController {
    private NoteService noteService;

    @Autowired
    public NoteController(NoteService service) {
        this.noteService = service;
    }


    @PostMapping("notes")
    public Note addNote(@NotNull @RequestBody Note note, @RequestHeader("X-Subject") String author) {
        return noteService.addNote(note, author);
    }


    @GetMapping("notes")
    public List<Note> getNotes(@RequestParam(value = "case_id", required = false) UUID caseID) {
        if (caseID != null) {
            return noteService.getNotes();
        }

        return noteService.getNotesByCaseID(caseID);
    }

    @DeleteMapping(path = "notes/{id}")
    public ResponseEntity<?> deleteNote(@PathVariable("id") UUID id) {
        return noteService.deleteNote(id);
    }

    @GetMapping
    public Note getNote(@PathVariable("id") UUID id) {
        return noteService.findNoteByID(id).orElseThrow(() -> new ResourceNotFoundException("note not found"));
    }
}
