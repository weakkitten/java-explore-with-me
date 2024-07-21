package ru.practicum.ewm_main.event.model.dto;

import lombok.experimental.UtilityClass;
import ru.practicum.ewm_main.categories.model.dto.CategoriesMapper;
import ru.practicum.ewm_main.categories.model.dto.CategoryDto;
import ru.practicum.ewm_main.event.model.Events;
import ru.practicum.ewm_main.event.model.Location;
import ru.practicum.ewm_main.user.model.dto.UserMapper;
import ru.practicum.ewm_main.user.model.dto.UserShortDto;
import ru.practicum.ewm_main.utility.State;
import ru.practicum.ewm_main.utility.StateActionAdmin;
import ru.practicum.ewm_main.utility.StateActionEvent;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@UtilityClass
public class EventsMapper {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public static EventShortDto toEventShortDto(Events events) {
        return EventShortDto.builder()
                .id(events.getId())
                .annotation(events.getAnnotation())
                .category(events.getCategory())
                .confirmedRequests(events.getConfirmedRequests())
                .eventDate(events.getEventDate())
                .paid(events.getPaid())
                .title(events.getTitle())
                .views(events.getViews())
                .initiator(events.getInitiator())
                .build();
    }

    public static Events toEvent(NewEventDto dto, int userId) {
        Events events = Events.builder()
                .annotation(dto.getAnnotation())
                .categoryId(dto.getCategory())
                .description(dto.getDescription())
                .eventDate(LocalDateTime.parse(dto.getEventDate(), formatter))
                .lat(dto.getLocation().getLat())
                .lon(dto.getLocation().getLon())
                .paid(dto.getPaid())
                .participantLimit(dto.getParticipantLimit())
                .title(dto.getTitle())
                .initiatorId(userId)
                .build();
        if (dto.getRequestModeration() == null) {
            events.setRequestModeration(true);
        } else {
            events.setRequestModeration(dto.getRequestModeration());
        }
        if (dto.getPaid() == null) {
            events.setPaid(false);
        } else {
            events.setPaid(dto.getPaid());
        }
        return events;
    }

    public static EventFullDto toEventFullDto(Events events, CategoryDto categoryDto, UserShortDto userShortDto) {
        return EventFullDto.builder()
                .id(events.getId())
                .annotation(events.getAnnotation())
                .category(categoryDto)
                .createdOn(events.getCreatedOn())
                .description(events.getDescription())
                .confirmedRequests(events.getConfirmedRequests())
                .eventDate(events.getEventDate())
                .initiator(userShortDto)
                .location(Location.builder()
                        .lat(events.getLat())
                        .lon(events.getLon())
                        .build())
                .paid(events.getPaid())
                .participantLimit(events.getParticipantLimit())
                .publishedOn(events.getPublishedOn())
                .requestModeration(events.getRequestModeration())
                .state(events.getState())
                .title(events.getTitle())
                .views(events.getViews())
                .build();
    }

    public static Events updateForUsers(Events events, UpdateEventUserRequest request) {
        if (request.getAnnotation() != null) {
            events.setAnnotation(request.getAnnotation());
        }
        if (request.getCategory() != null) {
            events.setCategoryId(request.getCategory());
        }
        if (request.getDescription() != null) {
            events.setDescription(request.getDescription());
        }
        if (request.getEventDate() != null) {
            events.setEventDate(LocalDateTime.parse(request.getEventDate(), formatter));
        }
        if (request.getLocation() != null) {
            events.setLat(request.getLocation().getLat());
            events.setLon(request.getLocation().getLon());
        }
        if (request.getPaid() != null) {
            events.setPaid(request.getPaid());
        }
        if (request.getParticipantLimit() != null) {
            events.setParticipantLimit(request.getParticipantLimit());
        }
        if (request.getRequestModeration() != null) {
            events.setRequestModeration(request.getRequestModeration());
        }
        if (request.getStateAction() != null) {
            if (request.getStateAction().equals(StateActionEvent.CANCEL_REVIEW)) {
                events.setState(State.CANCELED);
            } else {
                events.setState(State.PENDING);
            }
        }
        if (request.getTitle() != null) {
            events.setTitle(request.getTitle());
        }
        return events;
    }

    public static Events updateForAdmin(Events events, UpdateEventAdminRequest request) {
        if (request.getAnnotation() != null) {
            events.setAnnotation(request.getAnnotation());
        }
        if (request.getCategory() != null) {
            events.setCategoryId(request.getCategory());
        }
        if (request.getDescription() != null) {
            events.setDescription(request.getDescription());
        }
        if (request.getEventDate() != null) {
            events.setEventDate(LocalDateTime.parse(request.getEventDate(), formatter));
        }
        if (request.getLocation() != null) {
            events.setLat(request.getLocation().getLat());
            events.setLon(request.getLocation().getLon());
        }
        if (request.getPaid() != null) {
            events.setPaid(request.getPaid());
        }
        if (request.getParticipantLimit() != null) {
            events.setParticipantLimit(request.getParticipantLimit());
        }
        if (request.getRequestModeration() != null) {
            events.setRequestModeration(request.getRequestModeration());
        }
        if (request.getStateAction() != null) {
            if (request.getStateAction().equals(StateActionAdmin.PUBLISH_EVENT)) {
                events.setState(State.PUBLISHED);
            } else {
                events.setState(State.CANCELED);
            }
        }
        if (request.getTitle() != null) {
            events.setTitle(request.getTitle());
        }
        return events;
    }
}
