package ru.practicum.ewm_main.user.model.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class NewUserDto {
    private String email;
    private String name;
}
