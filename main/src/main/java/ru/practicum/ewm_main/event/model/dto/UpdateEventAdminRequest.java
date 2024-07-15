package ru.practicum.ewm_main.event.model.dto;

import ru.practicum.ewm_main.event.model.Location;
import ru.practicum.ewm_main.utility.StateActionAdmin;

import java.time.LocalDateTime;

public class UpdateEventAdminRequest {
    private String annotation;
    private int category;
    private String description;
    private LocalDateTime eventDate;
    private Location location;
    private boolean paid;
    private int participantLimit;
    private boolean requestModeration;
    private StateActionAdmin stateAction;
    private String title;
}
