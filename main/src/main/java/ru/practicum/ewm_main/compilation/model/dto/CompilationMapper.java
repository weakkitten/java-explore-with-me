package ru.practicum.ewm_main.compilation.model.dto;

import lombok.experimental.UtilityClass;
import ru.practicum.ewm_main.compilation.model.Compilation;

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
}
