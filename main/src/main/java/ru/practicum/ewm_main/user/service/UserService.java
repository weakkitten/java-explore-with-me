package ru.practicum.ewm_main.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.practicum.ewm_main.error.exception.BadRequestException;
import ru.practicum.ewm_main.event.model.Events;
import ru.practicum.ewm_main.event.model.dto.EventShortDto;
import ru.practicum.ewm_main.event.model.dto.EventsMapper;
import ru.practicum.ewm_main.event.model.dto.NewEventDto;
import ru.practicum.ewm_main.event.repository.EventsRepository;
import ru.practicum.ewm_main.request.model.EventRequestStatusUpdateRequest;
import ru.practicum.ewm_main.request.model.Request;
import ru.practicum.ewm_main.request.model.RequestDto;
import ru.practicum.ewm_main.request.model.RequestMapper;
import ru.practicum.ewm_main.request.repository.RequestRepository;
import ru.practicum.ewm_main.user.repository.UserRepository;
import ru.practicum.ewm_main.utility.Status;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final EventsRepository eventsRepository;
    private final RequestRepository requestRepository;

    public ResponseEntity<Object> getUsersEvents(int userId, int from, int size) {
        List<Events> userEventsList =  eventsRepository.findByInitiatorId(userId,
                                                                          PageRequest.of(from / size, size));
        List<EventShortDto> eventShortDtoList = new ArrayList<>();
        for (Events events : userEventsList) {
            eventShortDtoList.add(EventsMapper.toEventShortDto(events));
        }
        return ResponseEntity.ok(eventShortDtoList);
    }

    public ResponseEntity<Object> addNewEvents(int userId, NewEventDto dto) {
        Events events = EventsMapper.toEvent(dto);
        eventsRepository.save(events);
        EventShortDto shortDto = EventsMapper.toEventShortDto(eventsRepository.findByInitiatorIdAndAnnotation(userId,
                                                                                            events.getAnnotation()));
        return ResponseEntity.ok(shortDto);
    }

    public ResponseEntity<Object> getUserEventInfo(int userId, int eventId) {
        Events events = eventsRepository.findById(eventId).get();
        if (events.getInitiatorId() != userId) {
            throw new BadRequestException("Некорректный id пользователя");
        }
        EventShortDto shortDto = EventsMapper.toEventShortDto(eventsRepository.findById(eventId).get());
        return ResponseEntity.ok(shortDto);
    }

    public ResponseEntity<Object> updateUserEvent(int userId, int eventId) {
        if (eventsRepository.findById(eventId).isEmpty()) {
            throw new BadRequestException("Не найден");
        }
        Events events = eventsRepository.findById(eventId).get();
        EventShortDto shortDto = EventsMapper.toEventShortDto(eventsRepository.findById(eventId).get());
        if (events.getInitiatorId() != userId) {
            throw new BadRequestException("Некорректный id пользователя");
        }
        return ResponseEntity.ok(shortDto);
    }

    public ResponseEntity<Object> getUserEventRequest(int userId, int eventId) {
        List<Request> requestList = requestRepository.findByEvent(eventId);
        return ResponseEntity.ok(requestList);
    }

    public ResponseEntity<List<Request>> updateUserEventRequest(int userId,
                                                                int eventId,
                                                                EventRequestStatusUpdateRequest requestStatusUpdateRequest) {
        List<Request> requestList = requestRepository.findAllById(requestStatusUpdateRequest.getRequestsIds());
        for (Request request : requestList) {
            request.setStatus(Status.CONFIRMED);
        }
        requestRepository.saveAll(requestList);
        return ResponseEntity.ok(requestRepository.findAllById(requestStatusUpdateRequest.getRequestsIds()));
    }

    public Object getRequests(int userId) {
        List<Request> requestList = requestRepository.findByRequester(userId);
        List<RequestDto> requestDtoList = new ArrayList<>();
        for (Request request : requestList) {
            requestDtoList.add(RequestMapper.toRequestDto(request));
        }
        return ResponseEntity.ok(requestDtoList);
    }

    public Object addNewRequests(int userId) {
        return null;
    }

    public Object cancelRequests(int userId, int requestId) {
        return null;
    }
}
