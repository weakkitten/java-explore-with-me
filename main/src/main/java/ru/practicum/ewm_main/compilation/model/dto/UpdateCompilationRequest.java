package ru.practicum.ewm_main.compilation.model.dto;

import lombok.Builder;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;
import java.util.List;

@Data
@Builder
public class UpdateCompilationRequest {
    private List<Integer> events;
    private boolean pinned;
    @NotEmpty
    @NotEmpty
    @Length(max = 50)
    private String title;
}
