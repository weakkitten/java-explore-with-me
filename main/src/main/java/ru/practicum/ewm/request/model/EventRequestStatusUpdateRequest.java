package ru.practicum.ewm.request.model;

import lombok.Builder;
import lombok.Data;
import ru.practicum.ewm.utility.Status;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@Builder
public class EventRequestStatusUpdateRequest {
    @NotNull
    private List<Integer> requestIds;
    private Status status;
}
