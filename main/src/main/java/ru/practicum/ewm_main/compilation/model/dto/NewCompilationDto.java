package ru.practicum.ewm_main.compilation.model.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class NewCompilationDto {
    private List<Integer> events;
    private boolean pinned;
    private String title;
}
