package com.bd.webdiary.api;

import com.bd.webdiary.model.Note;
import com.bd.webdiary.service.NoteService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.ConstraintViolationException;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/notes")
public class NoteApi {
    private final NoteService noteService;
    
    public NoteApi(NoteService noteService){
        this.noteService = noteService;
    }

    @PostMapping
    public ResponseEntity create(@Valid @RequestBody Note note){
        return ResponseEntity.ok(this.noteService.save(note));
    }

    @GetMapping
    public ResponseEntity<List<Note>> getAll(){
        return ResponseEntity.ok(this.noteService.getAllActive());
    }

    @PostMapping("/update/{id}")
    public ResponseEntity<Note> update(@PathVariable Long id, @Valid @RequestBody Note note){
        return ResponseEntity.ok(this.noteService.update(id, note));
    }

    @PostMapping("/delete/{id}")
    public ResponseEntity<Note> inactivate(@PathVariable Long id) throws ConstraintViolationException {
        return ResponseEntity.ok(this.noteService.delete(id));
    }
    
}
