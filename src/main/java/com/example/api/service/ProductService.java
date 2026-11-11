package com.example.api.service;

import com.example.api.exception.DuplicateResourceException;
import com.example.api.exception.ResourceNotFoundException;
import com.example.api.model.dto.CategoryRequest;
import com.example.api.model.dto.ProductRequest;
import com.example.api.model.dto.StockQuantityUpdateRequest;
import com.example.api.model.entity.Category;
import com.example.api.model.entity.Product;
import com.example.api.repository.CategoryRepository;
import com.example.api.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    public Page<Product> getAllProducts(Boolean isAvailable, Pageable pageable) {
        Page<Product> productPage;
        if(isAvailable != null){
            productPage = productRepository.findByIsAvailable(isAvailable, pageable);
        }else{
            productPage = productRepository.findAll(pageable);
        }
        return productPage;
    }

    public Product getProductById(Long id){
        Product product = productRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Product not found"));
        return product;
    }

    public Product updateProduct(Long id, ProductRequest req){
        Product product = productRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Product not found"));
        Category category = categoryRepository.findById(req.getCategoryId()).orElseThrow(()->new ResourceNotFoundException("Category not found"));
        if(!product.getSku().equals(req.getSku()) && productRepository.existsBySku(req.getSku())){
            throw new DuplicateResourceException("Sku already exists");
        }
        product.setSku(req.getSku());
        product.setPrice(req.getPrice());
        product.setName(req.getName());
        product.setStockQuantity(req.getStockQuantity());
        product.setIsAvailable(req.getIsAvailable());
        product.setCategory(category);
        return productRepository.save(product);
    }

    public void deleteProduct(Long id){
        Product product = productRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Product not found"));
        productRepository.delete(product);
    }

    public Product updateStockQuantity(Long id, StockQuantityUpdateRequest req){
        Product product = productRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Product not found"));
        product.setStockQuantity(req.getStockQuantity());
        return productRepository.save(product);
    }

    public Product createProductForCategory(Long categoryId, ProductRequest req){
        Category category = categoryRepository.findById(categoryId).orElseThrow(()->new ResourceNotFoundException("Category not found"));
        if(productRepository.existsBySku(req.getSku())){
            throw new DuplicateResourceException("Sku already exists");
        }
        Product product = Product.builder()
                .sku(req.getSku())
                .name(req.getName())
                .price(req.getPrice())
                .stockQuantity(req.getStockQuantity())
                .isAvailable(req.getIsAvailable())
                .category(category)
                .build();
        return productRepository.save(product);
    }
}
