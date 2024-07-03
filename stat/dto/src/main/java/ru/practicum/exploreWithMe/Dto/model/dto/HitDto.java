package ru.practicum.exploreWithMe.Dto.model.dto;

import lombok.Builder;
import lombok.Data;

import javax.persistence.Entity;
import javax.validation.constraints.Null;
import java.time.LocalDateTime;

@Data
@Builder
public class HitDto {
    private String app;
    private String uri;
    private String ip;
    private String timestamp;
}
