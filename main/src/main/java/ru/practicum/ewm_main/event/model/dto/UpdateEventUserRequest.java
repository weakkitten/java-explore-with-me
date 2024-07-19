package ru.practicum.ewm_main.event.model.dto;

import lombok.Builder;
import lombok.Data;
import ru.practicum.ewm_main.event.model.Location;
import ru.practicum.ewm_main.utility.StateActionEvent;

import javax.validation.constraints.Positive;
import java.time.LocalDateTime;

@Data
@Builder
public class UpdateEventUserRequest {
    private String annotation;
    private Integer category;
    private String description;
    private LocalDateTime eventDate;
    private Location location;
    private Boolean paid;
    @Positive
    private Integer participantLimit;
    private Boolean requestModeration;
    private StateActionEvent stateAction;
    private String title;
}
