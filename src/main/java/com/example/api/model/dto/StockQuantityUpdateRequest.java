package com.example.api.model.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class StockQuantityUpdateRequest {
    @NotNull
    @Positive
    private Integer stockQuantity;
}
