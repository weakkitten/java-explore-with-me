package ru.practicum.ewm.request.model;

import lombok.Builder;
import lombok.Data;
import ru.practicum.ewm.utility.Status;
import java.time.LocalDateTime;

@Data
@Builder
public class RequestDto {
    private int id;
    private int requester;
    private LocalDateTime created;
    private Status status;
    private int event;
}
