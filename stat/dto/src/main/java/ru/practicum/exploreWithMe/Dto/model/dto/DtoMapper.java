package ru.practicum.exploreWithMe.Dto.model.dto;

import ru.practicum.exploreWithMe.Dto.model.Hit;
import ru.practicum.exploreWithMe.Dto.model.ViewStats;

public class DtoMapper {
    public static ViewStatsDto toViewStatsDto(ViewStats viewStats) {
        return ViewStatsDto.builder()
                .app(viewStats.getApp())
                .uri(viewStats.getUri())
                .hits(viewStats.getHits())
                .build();
    }

    public static HitDto toHitDto(Hit hit) {
        return HitDto.builder()
                .id(hit.getId())
                .ip(hit.getIp())
                .app(hit.getApp())
                .uri(hit.getUri())
                .timestamp(hit.getTimestamp())
                .build();
    }
}
