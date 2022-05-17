package com.bd.webdiary.api;

import com.bd.webdiary.model.Category;
import com.bd.webdiary.service.CategoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.ConstraintViolationException;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/categories")
public class CategoryApi {
    private final CategoryService categoryService;

    public CategoryApi(CategoryService categoryService){
        this.categoryService = categoryService;
    }

    @PostMapping
    public ResponseEntity create(@Valid @RequestBody Category category){
        return ResponseEntity.ok(this.categoryService.save(category));
    }

    @GetMapping
    public ResponseEntity<List<Category>> getAll(){
        return ResponseEntity.ok(this.categoryService.getAll());
    }

    @PostMapping("/update/{id}")
    public ResponseEntity<Category> update(@PathVariable Long id, @Valid @RequestBody Category category){
        return ResponseEntity.ok(this.categoryService.update(id, category));
    }

    @PostMapping("/delete/{id}")
    public ResponseEntity<Category> inactivate(@PathVariable Long id) throws ConstraintViolationException {
        return ResponseEntity.ok(this.categoryService.delete(id));
    }
}
