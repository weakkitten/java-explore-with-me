package ru.practicum.ewm_main.request.model;

import lombok.Builder;
import lombok.Data;
import ru.practicum.ewm_main.utility.Status;
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
