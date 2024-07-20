package ru.practicum.ewm_main.categories.model.dto;

import lombok.*;
import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NewCategoryDto {
    @NotEmpty
    private String name;
}
