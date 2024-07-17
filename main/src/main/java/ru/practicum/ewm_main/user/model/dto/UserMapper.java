package ru.practicum.ewm_main.user.model.dto;

import lombok.experimental.UtilityClass;
import ru.practicum.ewm_main.user.model.User;

@UtilityClass
public class UserMapper {
    public static User toUser(UserDto dto) {
        return User.builder()
                .id(dto.getId())
                .name(dto.getName())
                .email(dto.getEmail())
                .build();
    }

    public static UserShortDto userShortDto(User user) {
        return UserShortDto.builder()
                .id(user.getId())
                .name(user.getName())
                .build();
    }
}
