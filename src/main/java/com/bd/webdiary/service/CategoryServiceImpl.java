package com.bd.webdiary.service;

import com.bd.webdiary.util.CommonConstant;
import com.bd.webdiary.model.Category;
import com.bd.webdiary.repository.CategoryRepository;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService{
    private final CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository){
        this.categoryRepository = categoryRepository;
    }

    @Override
    public Category save(Category category) {
        if(category.getId() == 0){
           category.setStatusId(CommonConstant.STATUS_ID_ACTIVE);
        }

        return this.categoryRepository.saveAndFlush(category);
    }

    @Override
    public Category update(long id, Category category) {
        Optional<Category> categoryOptional = readById(id);
        if(!categoryOptional.isPresent()){
            return save(category);
        }else {
            categoryOptional.map(existingCategory -> {
                existingCategory.setCategoryName(category.getCategoryName());
                existingCategory.setStatusId(category.getStatusId());
                return save(existingCategory);
            });
        }

        return null;
    }

    @Override
    public Category delete(long id) {
        Optional<Category> categoryOptional = readById(id);
        if(!categoryOptional.isPresent()){
            throw new ConstraintViolationException("ID " + id + " not found", null);
        } else {
            categoryOptional.map(existingCategory -> {
                existingCategory.setStatusId(CommonConstant.STATUS_ID_INACTIVE);
                return save(existingCategory);
            });
        }

        return null;
    }

    @Override
    public List<Category> getAllActive() {
        return this.categoryRepository.findAllByStatusId(CommonConstant.STATUS_ID_ACTIVE);
    }

    @Override
    public List<Category> getAll() {
        return this.categoryRepository.findAll();
    }

    @Override
    public Optional<Category> readById(long id) {
        return this.categoryRepository.readById(id);
    }


}
