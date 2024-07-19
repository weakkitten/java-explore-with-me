package ru.practicum.ewm_main.event.model.dto;

import lombok.Builder;
import lombok.Data;
import ru.practicum.ewm_main.event.model.Location;

import javax.validation.constraints.*;

@Data
@Builder
public class NewEventDto {
    @NotEmpty
    @NotBlank
    private String annotation;
    private Integer category;
    @NotEmpty
    @NotBlank
    private String description;
    private String eventDate;
    private Location location;
    private Boolean paid;
    @Positive
    private int participantLimit;
    private Boolean requestModeration;
    private String title;
}
