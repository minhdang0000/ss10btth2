package com.example.api.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String sku;
    private String name;
    private BigDecimal price;
    @NotNull
    @Positive
    private Integer stockQuantity;
    private Boolean isAvailable;
    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;
}
