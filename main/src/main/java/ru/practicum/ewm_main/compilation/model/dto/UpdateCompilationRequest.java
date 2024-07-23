package ru.practicum.ewm_main.compilation.model.dto;

import lombok.Builder;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import java.util.List;

@Data
@Builder
public class UpdateCompilationRequest {
    private List<Integer> events;
    private boolean pinned;
    @Length(max = 50)
    private String title;
}
