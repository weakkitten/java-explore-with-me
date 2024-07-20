package ru.practicum.ewm_main.event.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Location {
    private float lat;
    private float lon;
}
