package ru.practicum.ewm_main.compilation.model.dto;

import lombok.Builder;
import lombok.Data;
import ru.practicum.ewm_main.event.model.dto.EventShortDto;

import java.util.List;

@Data
@Builder
public class CompilationDto {
    private int id;
    private List<EventShortDto> events;
    private boolean pinned;
    private String title;
}