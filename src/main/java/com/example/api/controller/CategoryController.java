package com.example.api.controller;

import com.example.api.model.dto.CategoryRequest;
import com.example.api.model.dto.ProductRequest;
import com.example.api.model.entity.Category;
import com.example.api.model.entity.Product;
import com.example.api.service.CategoryService;
import com.example.api.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/categories")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;
    private final ProductService productService;

    @GetMapping
    public ResponseEntity<List<Category>> getAllCategories() {
        return ResponseEntity.ok(categoryService.getAllCategories());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Category> getCategoryById(@PathVariable Long id){
        return ResponseEntity.ok(categoryService.getCategoryById(id));
    }

    @PostMapping
    public ResponseEntity<Category> createCategory(@Valid @RequestBody CategoryRequest req) {
        return new ResponseEntity<>(categoryService.createCategory(req), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Category> updateCategory(@PathVariable Long id, @Valid @RequestBody CategoryRequest req) {
        return ResponseEntity.ok(categoryService.updateCategory(id, req));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Category> deleteCategory(@PathVariable Long id){
        categoryService.deleteCategory(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/products")
    public ResponseEntity<List<Product>> getProductsByCategoryId(@PathVariable Long id){
        return ResponseEntity.ok(categoryService.getProductsByCategoryId(id));
    }

    @PostMapping("/{id}/products")
    public ResponseEntity<Product> createProductForCategory(@PathVariable Long id, @Valid @RequestBody ProductRequest req) {
        return new ResponseEntity<>(productService.createProductForCategory(id, req), HttpStatus.CREATED);
    }
}
