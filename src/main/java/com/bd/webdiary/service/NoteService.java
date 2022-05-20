package com.bd.webdiary.service;


import com.bd.webdiary.model.Note;

import java.util.List;
import java.util.Optional;

public interface NoteService {
    public Note save(Note note);
    public Note update(long id, Note note);
    public Note delete(long id);
    public List<Note> getAllActive();
    public List<Note> getAllByCategory(long categoryId);
    public Optional<Note> readById(long id);
}
