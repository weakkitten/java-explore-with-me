package ru.practicum.exploreWithMe.Dto.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Hit {
    private Integer id;
    private String app;
    private String uri;
    private String ip;
    private String timestamp;
}
