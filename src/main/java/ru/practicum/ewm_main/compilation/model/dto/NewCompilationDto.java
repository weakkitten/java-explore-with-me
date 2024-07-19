package ru.practicum.ewm_main.compilation.model.dto;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import java.util.List;

@Data
@Builder
@ToString
public class NewCompilationDto {
    private List<Integer> events;
    private boolean pinned;
    private String title;
}
