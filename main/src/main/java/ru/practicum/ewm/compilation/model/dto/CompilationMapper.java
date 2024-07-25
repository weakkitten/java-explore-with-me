package ru.practicum.ewm.compilation.model.dto;

import lombok.experimental.UtilityClass;
import ru.practicum.ewm.compilation.model.Compilation;
import ru.practicum.ewm.event.model.dto.EventShortDto;

import java.util.List;

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

    public static CompilationDto toCompilationDto(Compilation compilation, List<EventShortDto> dto) {
        return CompilationDto.builder()
                .id(compilation.getId())
                .events(dto)
                .pinned(compilation.isPinned())
                .title(compilation.getTitle())
                .build();
    }
}
