package com.bd.webdiary.repository;

import com.bd.webdiary.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    List<Category> findAllByStatusIdOrderByIdAsc(int statusId);
    Optional<Category> readById(long id);
}
