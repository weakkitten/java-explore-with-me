package ru.practicum.ewm_main.event.model.dto;

import ru.practicum.ewm_main.categories.model.dto.CategoryDto;
import ru.practicum.ewm_main.user.model.dto.UserShortDto;

import java.time.LocalDateTime;

public class EventShortDto {
    private int id;
    private String annotation;
    private CategoryDto category;
    private int confirmedRequests;
    private LocalDateTime eventDate;
    private UserShortDto initiator;
    private boolean paid;
    private String title;
    private int views;
}
