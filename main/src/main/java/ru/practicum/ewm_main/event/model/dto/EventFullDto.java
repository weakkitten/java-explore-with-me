package ru.practicum.ewm_main.event.model.dto;

import ru.practicum.ewm_main.categories.model.dto.CategoryDto;
import ru.practicum.ewm_main.event.model.Location;
import ru.practicum.ewm_main.user.model.dto.UserShortDto;
import ru.practicum.ewm_main.utility.State;

import java.time.LocalDateTime;

public class EventFullDto {
    private int id;
    private String annotation;
    private CategoryDto category;
    private LocalDateTime createdOn;
    private String description;
    private int confirmedRequests;
    private LocalDateTime eventDate;
    private UserShortDto initiator;
    private Location location;
    private boolean paid;
    private int participantLimit;
    private LocalDateTime publishedOn;
    private boolean requestModeration;
    private State state;
    private String title;
    private int views;
}
