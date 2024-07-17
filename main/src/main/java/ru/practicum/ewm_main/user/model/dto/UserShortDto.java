package ru.practicum.ewm_main.user.model.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserShortDto {
    private int id;
    private String name;
}
