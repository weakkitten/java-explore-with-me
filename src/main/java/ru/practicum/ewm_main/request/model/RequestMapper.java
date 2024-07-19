package ru.practicum.ewm_main.request.model;

import lombok.experimental.UtilityClass;

@UtilityClass
public class RequestMapper {
    public static Request toRequest(RequestDto dto) {
        return Request.builder()
                .requester(dto.getRequester())
                .created(dto.getCreated())
                .event(dto.getEvent())
                .status(dto.getStatus())
                .build();
    }

    public static RequestDto toRequestDto(Request request) {
        return RequestDto.builder()
                .id(request.getId())
                .created(request.getCreated())
                .requester(request.getRequester())
                .event(request.getEvent())
                .status(request.getStatus())
                .build();
    }
}
