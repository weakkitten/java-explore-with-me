package ru.practicum.ewm_main.compilation.model.dto;

import lombok.experimental.UtilityClass;
import ru.practicum.ewm_main.compilation.model.Compilation;
import ru.practicum.ewm_main.event.model.dto.EventShortDto;

@UtilityClass
public class CompilationMapper {
    public static Compilation updateCompilation(UpdateCompilationRequest updateCompilationRequest,
                                                Compilation compilation) {
        compilation.setTitle(updateCompilationRequest.getTitle());
        compilation.setPinned(updateCompilationRequest.isPinned());
        return compilation;
    }

    public static Compilation toCompilation(NewCompilationDto dto) {
        return Compilation.builder()
                .title(dto.getTitle())
                .pinned(dto.isPinned())
                .build();
    }

    public static CompilationDto toCompilationDto(Compilation compilation, EventShortDto dto) {
        return CompilationDto.builder()
                .id(compilation.getId())
                .events(dto)
                .pinned(compilation.isPinned())
                .title(compilation.getTitle())
                .build();
    }
}
