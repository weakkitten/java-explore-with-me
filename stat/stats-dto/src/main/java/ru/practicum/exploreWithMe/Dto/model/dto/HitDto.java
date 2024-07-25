package ru.practicum.exploreWithMe.Dto.model.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class HitDto {
    private int id;
    private String app;
    private String uri;
    private String ip;
    private String timestamp;
}
