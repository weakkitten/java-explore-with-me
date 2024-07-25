package ru.practicum.ewm.request.model;

import lombok.Builder;
import lombok.Data;
import ru.practicum.ewm.utility.Status;

@Data
@Builder
public class ParticipationRequestDto {
    private int id;
    private String created;
    private int event;
    private int requester;
    private Status status;
}
