package ru.practicum.ewm.Dto.model.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class NewHit {
    private String app;
    private String uri;
    private String ip;
    private String timestamp;
}
