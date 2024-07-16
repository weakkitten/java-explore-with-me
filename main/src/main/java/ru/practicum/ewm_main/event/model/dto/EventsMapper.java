package ru.practicum.ewm_main.event.model.dto;

import lombok.experimental.UtilityClass;
import ru.practicum.ewm_main.event.model.Events;
import ru.practicum.ewm_main.utility.State;
import ru.practicum.ewm_main.utility.StateActionAdmin;

@UtilityClass
public class EventsMapper {
    public static Events updateEvents(Events events, UpdateEventAdminRequest request) {
        events.setAnnotation(request.getAnnotation());
        events.setCategoryId(request.getCategory());
        events.setDescription(request.getDescription());
        events.setEventDate(request.getEventDate());
        events.setLat(request.getLocation().getLat());
        events.setLon(request.getLocation().getLon());
        events.setPaid(request.isPaid());
        events.setParticipantLimit(request.getParticipantLimit());
        events.setRequestModeration(request.isRequestModeration());
        if (request.getStateAction() == StateActionAdmin.PUBLISH_EVENT) {
            events.setState(State.PUBLISHED);
        } else {
            events.setState(State.CANCELED);
        }
        events.setTitle(request.getTitle());
        return events;
    }
}
