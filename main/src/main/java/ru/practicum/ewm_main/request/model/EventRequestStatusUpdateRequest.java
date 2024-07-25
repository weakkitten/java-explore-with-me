package ru.practicum.ewm_main.request.model;

import lombok.Builder;
import lombok.Data;
import ru.practicum.ewm_main.utility.Status;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@Builder
public class EventRequestStatusUpdateRequest {
    @NotNull
    private List<Integer> requestIds;
    private Status status;
}
