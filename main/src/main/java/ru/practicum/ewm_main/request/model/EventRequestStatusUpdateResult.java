package ru.practicum.ewm_main.request.model;

import lombok.Builder;
import lombok.Data;
import ru.practicum.ewm_main.event.model.dto.ParticipationRequestDto;

import java.util.List;

@Data
@Builder
public class EventRequestStatusUpdateResult {
    private List<ParticipationRequestDto> confirmedRequests;
    private List<ParticipationRequestDto> rejectedRequests;
}
