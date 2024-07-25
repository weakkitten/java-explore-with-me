package ru.practicum.ewm.Dto.model.dto;

import lombok.experimental.UtilityClass;
import ru.practicum.ewm.Dto.model.Hit;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@UtilityClass
public class HitMapper {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public static Hit toHit(NewHit newHit) {
        return Hit.builder()
                .uri(newHit.getUri())
                .ip(newHit.getIp())
                .app(newHit.getApp())
                .timestamp(LocalDateTime.parse(newHit.getTimestamp(), formatter))
                .build();
    }

    public static HitDto toHitDto(Hit hit) {
        return HitDto.builder()
                .ip(hit.getIp())
                .uri(hit.getUri())
                .ip(hit.getIp())
                .app(hit.getApp())
                .timestamp(formatter.format(hit.getTimestamp()))
                .build();
    }
}
