package ru.practicum.ewm.event.model.dto;

import lombok.Builder;
import lombok.Data;
import ru.practicum.ewm.categories.model.Categories;
import ru.practicum.ewm.user.model.User;

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
