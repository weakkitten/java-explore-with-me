package ru.practicum.ewm_main.event.model.dto;

import lombok.Builder;
import lombok.Data;
import ru.practicum.ewm_main.categories.model.Categories;
import ru.practicum.ewm_main.categories.model.dto.CategoryDto;
import ru.practicum.ewm_main.event.model.Location;
import ru.practicum.ewm_main.user.model.User;
import ru.practicum.ewm_main.user.model.dto.UserShortDto;
import ru.practicum.ewm_main.utility.State;

import java.time.LocalDateTime;

@Data
@Builder
public class EventFullDto {
    private Integer id;
    private String annotation;
    private Categories category;
    private LocalDateTime createdOn;
    private String description;
    private int confirmedRequests;
    private LocalDateTime eventDate;
    private User initiator;
    private Location location;
    private Boolean paid;
    private Integer participantLimit;
    private LocalDateTime publishedOn;
    private Boolean requestModeration;
    private State state;
    private String title;
    private int views;
}
