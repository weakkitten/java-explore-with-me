package ru.practicum.ewm_main.compilation.model.dto;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@Data
@Builder
@ToString
public class NewCompilationDto {
    private List<Integer> events;
    private boolean pinned;
    @NotBlank
    @NotEmpty
    @Length(max = 50)
    private String title;
}
