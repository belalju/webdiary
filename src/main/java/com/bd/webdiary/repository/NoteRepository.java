package com.bd.webdiary.repository;

import com.bd.webdiary.model.Note;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface NoteRepository extends JpaRepository<Note, Long> {
    @Query(value = "SELECT n.id, n.title, n.content, n.last_updated_time, n.category_id, n.status_id, n.user_id, c.category_name"
            + " FROM note n INNER JOIN category c on n.category_id = c.id WHERE n.user_id = ? AND n.status_id = ? ORDER BY n.id ASC", nativeQuery = true)
    List<Note> findAllByStatusId(long userId, int statusId);

    @Query(value = "SELECT n.id, n.title, n.content, n.last_updated_time, n.category_id, n.status_id, n.user_id, c.category_name"
            + " FROM note n INNER JOIN category c on n.category_id = c.id WHERE n.user_id = ? AND n.status_id = ? AND n.category_id = ? ORDER BY n.id ASC", nativeQuery = true)
    List<Note> findAllByCategoryId(long userId, int statusId, long categoryId);

    Optional<Note> readById(long id);
}
