package com.example.api.service;

import com.example.api.exception.DuplicateResourceException;
import com.example.api.exception.ResourceNotFoundException;
import com.example.api.model.dto.CategoryRequest;
import com.example.api.model.entity.Category;
import com.example.api.model.entity.Product;
import com.example.api.repository.CategoryRepository;
import com.example.api.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;

    public List<Category> getAllCategories(){
        return categoryRepository.findAll();
    }

    public Category getCategoryById(Long id){
        Category category = categoryRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Category not found"));
        return category;
    }

    public Category createCategory(CategoryRequest req){
        if(categoryRepository.existsByCode(req.getCode())){
            throw new DuplicateResourceException("Code already exists");
        }
        Category category = Category.builder()
                .name(req.getName())
                .code(req.getCode())
                .description(req.getDescription())
                .build();
        return categoryRepository.save(category);
    }

    public Category updateCategory(Long id, CategoryRequest req){
        Category category = categoryRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Category not found"));
        if(!category.getCode().equals(req.getCode()) && categoryRepository.existsByCode(req.getCode())){
           throw new DuplicateResourceException("Code already exists");
        }
        category.setName(req.getName());
        category.setDescription(req.getDescription());
        category.setCode(req.getCode());
        return categoryRepository.save(category);
    }

    public void deleteCategory(Long id){
        Category category = categoryRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Category not found"));
        categoryRepository.delete(category);
    }

    public List<Product> getProductsByCategoryId(Long categoryId){
        if(!categoryRepository.existsById(categoryId)){
            throw new ResourceNotFoundException("Category not found");
        }
        return productRepository.findByCategoryId(categoryId);
    }
}
