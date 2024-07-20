package ru.practicum.ewm_main.event.model.dto;

import lombok.Builder;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import ru.practicum.ewm_main.event.model.Location;

import javax.validation.constraints.*;

@Data
@Builder
public class NewEventDto {
    @NotEmpty
    @NotBlank
    @Length(min = 20, max = 2000)
    private String annotation;
    private Integer category;
    @NotEmpty
    @NotBlank
    @Length(min = 20, max = 7000)
    private String description;
    private String eventDate;
    private Location location;
    private Boolean paid = false;
    @PositiveOrZero
    private int participantLimit;
    private Boolean requestModeration = true;
    @Length(min = 3, max = 120)
    private String title;
}
