package ru.practicum.ewm.event.model.dto;

import lombok.Builder;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import ru.practicum.ewm.event.model.Location;
import ru.practicum.ewm.utility.StateActionEvent;
import javax.validation.constraints.Positive;

@Data
@Builder
public class UpdateEventUserRequest {
    @Length(min = 20, max = 2000)
    private String annotation;
    private Integer category;
    @Length(min = 20, max = 7000)
    private String description;
    private String eventDate;
    private Location location;
    private Boolean paid;
    @Positive
    private Integer participantLimit;
    private Boolean requestModeration;
    private StateActionEvent stateAction;
    @Length(min = 3, max = 120)
    private String title;
}
