package com.bd.webdiary.service;

import com.bd.webdiary.model.Note;
import com.bd.webdiary.repository.NoteRepository;
import com.bd.webdiary.util.CommonConstant;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolationException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Service
public class NoteServiceImpl implements NoteService {
    private final NoteRepository noteRepository;

    public NoteServiceImpl(NoteRepository noteRepository){
        this.noteRepository = noteRepository;
    }

    @Override
    public Note save(Note note) {
        if(note.getId() == 0){
            note.setUserId(1);
        }

        note.setStatusId(CommonConstant.STATUS_ID_ACTIVE);
        note.setLastUpdatedTime(LocalDateTime.now());

        return this.noteRepository.saveAndFlush(note);
    }

    @Override
    public Note update(long id, Note note) {
        Optional<Note> noteOptional = readById(id);
        if(!noteOptional.isPresent()){
            return save(note);
        }else {
            noteOptional.map(existingCategory -> {
                existingCategory.setCategoryId(note.getCategoryId());
                existingCategory.setTitle(note.getTitle());
                existingCategory.setContent(note.getContent());
                existingCategory.setStatusId(note.getStatusId());
                existingCategory.setLastUpdatedTime(LocalDateTime.now());
                return save(existingCategory);
            });
        }

        return null;
    }

    @Override
    public Note delete(long id) {
        Optional<Note> noteOptional = readById(id);
        if(!noteOptional.isPresent()){
            throw new ConstraintViolationException("ID " + id + " not found", null);
        } else {
            noteOptional.map(existingNote -> {
                existingNote.setStatusId(CommonConstant.STATUS_ID_INACTIVE);
                return save(existingNote);
            });
        }

        return null;
    }

    @Override
    public List<Note> getAllActive() {
        return this.noteRepository.findAllByStatusId(CommonConstant.STATUS_ID_ACTIVE);
    }

    @Override
    public Optional<Note> readById(long id) {
        return this.noteRepository.readById(id);
    }
}
