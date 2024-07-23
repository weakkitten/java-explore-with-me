package ru.practicum.ewm_main.request.model;

import lombok.Builder;
import lombok.Data;
import ru.practicum.ewm_main.utility.Status;

import java.time.LocalDateTime;

@Data
@Builder
public class ParticipationRequestDto {
    private int id;
    private String created;
    private int event;
    private int requester;
    private Status status;
}
