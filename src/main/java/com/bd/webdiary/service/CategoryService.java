package com.bd.webdiary.service;

import com.bd.webdiary.model.Category;

import java.util.List;
import java.util.Optional;

public interface CategoryService {
    public Category save(Category category);
    public Category update(long id, Category category);
    public Category delete(long id);
    public List<Category> getAllActive();
    public List<Category> getAll();
    public Optional<Category> readById(long id);
}
