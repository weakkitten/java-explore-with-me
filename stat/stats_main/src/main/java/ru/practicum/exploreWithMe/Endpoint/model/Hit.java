package ru.practicum.exploreWithMe.Endpoint.model;

import lombok.*;
import java.time.LocalDateTime;

@Data
public class Hit {
    private int id;
    private String app;
    private String uri;
    private String ip;
    private LocalDateTime timestamp;
}
