package ru.practicum.ewm.categories.model.dto;

import lombok.experimental.UtilityClass;
import ru.practicum.ewm.categories.model.Categories;

@UtilityClass
public class CategoriesMapper {
    public static Categories toCategories(NewCategoryDto dto) {
        return Categories.builder()
                .name(dto.getName())
                .build();
    }

    public static CategoryDto toCategoryDto(Categories categories) {
        return CategoryDto.builder()
                .id(categories.getId())
                .name(categories.getName())
                .build();
    }
}
