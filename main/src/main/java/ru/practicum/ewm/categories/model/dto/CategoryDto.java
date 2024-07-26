package ru.practicum.ewm.categories.model.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CategoryDto {
    private int id;
    private String name;
}
