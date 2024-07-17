package ru.practicum.ewm_main.request.model;

import lombok.Builder;
import lombok.Data;
import ru.practicum.ewm_main.utility.Status;

import java.util.List;

@Data
@Builder
public class EventRequestStatusUpdateRequest {
    private List<Integer> requestsIds;
    private Status status;
}
