package ru.practicum.exploreWithMe.Endpoint.model;

import lombok.*;

@Data
public class Hit {
    private int id;
    private String app;
    private String uri;
    private String ip;
    private String timestamp;
}
