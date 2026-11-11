package com.example.api.model.dto;

import com.example.api.model.entity.Category;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ProductRequest {
    @NotBlank
    @Size(min = 8, max = 12)
    private String sku;
    @NotBlank
    @Size(min = 5, max = 200)
    private String name;
    @NotNull
    @Positive
    private BigDecimal price;
    private Integer stockQuantity;
    @NotNull
    private Boolean isAvailable;
    private Long categoryId;
}
