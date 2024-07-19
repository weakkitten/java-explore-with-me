package ru.practicum.ewm_main.event.model.dto;

import lombok.Builder;
import lombok.Data;
import ru.practicum.ewm_main.event.model.Location;
import ru.practicum.ewm_main.utility.StateActionAdmin;

import java.time.LocalDateTime;

@Data
@Builder
public class UpdateEventAdminRequest {
    private String annotation;
    private Integer category;
    private String description;
    private LocalDateTime eventDate;
    private Location location;
    private Boolean paid;
    private Integer participantLimit;
    private Boolean requestModeration;
    private StateActionAdmin stateAction;
    private String title;
}
