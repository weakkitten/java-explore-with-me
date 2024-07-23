package ru.practicum.ewm_main.event.model.dto;

import lombok.Builder;
import lombok.Data;
import ru.practicum.ewm_main.categories.model.Categories;
import ru.practicum.ewm_main.user.model.User;

import java.time.LocalDateTime;

@Data
@Builder
public class EventShortDto {
    private Integer id;
    private String annotation;
    private Categories category;
    private int confirmedRequests;
    private String eventDate;
    private User initiator;
    private boolean paid;
    private String title;
    private int views;
}
