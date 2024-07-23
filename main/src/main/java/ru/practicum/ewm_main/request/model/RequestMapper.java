package ru.practicum.ewm_main.request.model;

import lombok.experimental.UtilityClass;

import java.time.format.DateTimeFormatter;

@UtilityClass
public class RequestMapper {
    public static ParticipationRequestDto toRequestDto(Request request) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return ParticipationRequestDto.builder()
                .id(request.getId())
                .created(formatter.format(request.getCreated()))
                .requester(request.getRequester())
                .event(request.getEvent())
                .status(request.getStatus())
                .build();
    }
}
