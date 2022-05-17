package com.bd.webdiary.repository;

import com.bd.webdiary.model.Note;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface NoteRepository extends JpaRepository<Note, Long> {
    List<Note> findAllByStatusId(int statusId);
    Optional<Note> readById(long id);
}
