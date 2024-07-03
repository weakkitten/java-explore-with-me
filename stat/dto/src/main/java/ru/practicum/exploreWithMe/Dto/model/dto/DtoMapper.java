package ru.practicum.exploreWithMe.Dto.model.dto;

import ru.practicum.exploreWithMe.Dto.model.Hit;
import ru.practicum.exploreWithMe.Dto.model.ViewStats;
import java.time.LocalDateTime;

public class DtoMapper {
    public static ViewStatsDto toViewStatsDto(ViewStats viewStats) {
        return ViewStatsDto.builder()
                .app(viewStats.getApp())
                .uri(viewStats.getUri())
                .hits(viewStats.getHits())
                .build();
    }

    public static Hit toHit(HitDto dto) {
        return Hit.builder()
                .ip(dto.getIp())
                .app(dto.getApp())
                .uri(dto.getUri())
                .timestamp(LocalDateTime.parse(dto.getTimestamp()))
                .build();
    }
}
