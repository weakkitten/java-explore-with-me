package ru.practicum.ewm.event.model.dto;

import lombok.Builder;
import lombok.Data;
import ru.practicum.ewm.categories.model.dto.CategoryDto;
import ru.practicum.ewm.event.model.Location;
import ru.practicum.ewm.user.model.dto.UserShortDto;
import ru.practicum.ewm.utility.State;

import java.time.LocalDateTime;

@Data
@Builder
public class EventFullDto {
    private Integer id;
    private String annotation;
    private CategoryDto category;
    private LocalDateTime createdOn;
    private String description;
    private int confirmedRequests;
    private String eventDate;
    private UserShortDto initiator;
    private Location location;
    private Boolean paid;
    private Integer participantLimit;
    private LocalDateTime publishedOn;
    private Boolean requestModeration;
    private State state;
    private String title;
    private int views;
}
