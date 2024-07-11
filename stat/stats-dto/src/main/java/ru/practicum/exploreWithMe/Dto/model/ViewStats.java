package ru.practicum.exploreWithMe.Dto.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class ViewStats {
    public String app;
    public String uri;
    public Integer hits;
}
