package ru.practicum.ewm.event.model;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class Hit {
    private String app;
    private String uri;
    private String ip;
    private LocalDateTime timestamp;
}
