package ru.practicum.ewm_main.event.model.dto;

import ru.practicum.ewm_main.event.model.Location;

public class NewEventDto {
    private String annotation;
    private int category;
    private String description;
    private String eventDate;
    private Location location;
    private boolean paid;
    private int participantLimit;
    private boolean requestModeration;
    private String title;
}
