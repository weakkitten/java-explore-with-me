package ru.practicum.ewm_main.event.model.dto;

import ru.practicum.ewm_main.event.model.Location;
import ru.practicum.ewm_main.utility.StateActionEvent;

import java.time.LocalDateTime;

public class UpdateEventUserRequest {
    private String annotation;
    private int category;
    private String description;
    private LocalDateTime eventDate;
    private Location location;
    private boolean paid;
    private int participantLimit;
    private boolean requestModeration;
    private StateActionEvent stateAction;
    private String title;
}
