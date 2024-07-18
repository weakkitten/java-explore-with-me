package ru.practicum.ewm_main.event.model.dto;

import lombok.experimental.UtilityClass;
import ru.practicum.ewm_main.categories.model.dto.CategoriesMapper;
import ru.practicum.ewm_main.event.model.Events;
import ru.practicum.ewm_main.user.model.dto.UserMapper;
import ru.practicum.ewm_main.utility.State;
import ru.practicum.ewm_main.utility.StateActionAdmin;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

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

    public static EventShortDto toEventShortDto(Events events) {
        return EventShortDto.builder()
                .id(events.getId())
                .annotation(events.getAnnotation())
                .category(CategoriesMapper.toCategoryDto(events.getCategory()))
                .confirmedRequests(events.getConfirmedRequests())
                .eventDate(events.getEventDate())
                .paid(events.isPaid())
                .title(events.getTitle())
                .views(events.getViews())
                .initiator(UserMapper.userShortDto(events.getInitiator()))
                .build();
    }

    public static Events toEvent(NewEventDto dto, int userId) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return Events.builder()
                .annotation(dto.getAnnotation())
                .categoryId(dto.getCategory())
                .description(dto.getDescription())
                .eventDate(LocalDateTime.parse(dto.getEventDate(), formatter))
                .lat(dto.getLocation().getLat())
                .lon(dto.getLocation().getLon())
                .paid(dto.isPaid())
                .participantLimit(dto.getParticipantLimit())
                .requestModeration(dto.isRequestModeration())
                .title(dto.getTitle())
                .initiatorId(userId)
                .build();
    }
}
