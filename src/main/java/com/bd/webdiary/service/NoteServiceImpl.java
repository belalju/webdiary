package com.bd.webdiary.service;

import com.bd.webdiary.model.Note;
import com.bd.webdiary.repository.NoteRepository;
import com.bd.webdiary.util.CommonConstant;
import com.bd.webdiary.util.UserUtil;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolationException;
import java.time.LocalDateTime;
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
        note.setUserId(UserUtil.getUser().getId());
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
               // existingCategory.setStatusId(note.getStatusId());
                existingCategory.setLastUpdatedTime(LocalDateTime.now());
                return this.noteRepository.saveAndFlush(existingCategory);
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
            Note note = noteOptional.get();
            note.setStatusId(CommonConstant.STATUS_ID_INACTIVE);
            note.setLastUpdatedTime(LocalDateTime.now());
            return this.noteRepository.saveAndFlush(note);
        }

    }

    @Override
    public List<Note> getAllActive() {
        return this.noteRepository.findAllByStatusId(UserUtil.getUser().getId(), CommonConstant.STATUS_ID_ACTIVE);
    }

    @Override
    public List<Note> getAllByCategory(long categoryId) {
        return this.noteRepository.findAllByCategoryId(UserUtil.getUser().getId(), CommonConstant.STATUS_ID_ACTIVE, categoryId);
    }

    @Override
    public Optional<Note> readById(long id) {
        return this.noteRepository.readById(id);
    }
}
