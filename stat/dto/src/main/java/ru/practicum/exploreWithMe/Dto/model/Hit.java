package ru.practicum.exploreWithMe.Dto.model;

import lombok.Builder;
import lombok.Data;

import javax.persistence.Entity;
import java.time.LocalDateTime;

@Data
@Builder
public class Hit {
    private int id;
    private String app;
    private String uri;
    private String ip;
    private String timestamp;
}
