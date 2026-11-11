package com.example.api.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class CategoryRequest {
    @NotBlank
    @Size(min = 3, max = 15)
    private String code;
    @NotBlank
    @Size(max = 100)
    private String name;
    private String description;
}
