package ru.practicum.ewm_main.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.ewm_main.categories.model.dto.CategoriesMapper;
import ru.practicum.ewm_main.categories.model.dto.CategoryDto;
import ru.practicum.ewm_main.categories.repository.CategoriesRepository;
import ru.practicum.ewm_main.error.exception.BadRequestException;
import ru.practicum.ewm_main.error.exception.NotFoundException;
import ru.practicum.ewm_main.event.model.Events;
import ru.practicum.ewm_main.event.model.dto.*;
import ru.practicum.ewm_main.event.repository.EventsRepository;
import ru.practicum.ewm_main.request.model.EventRequestStatusUpdateRequest;
import ru.practicum.ewm_main.request.model.Request;
import ru.practicum.ewm_main.request.model.RequestDto;
import ru.practicum.ewm_main.request.model.RequestMapper;
import ru.practicum.ewm_main.request.repository.RequestRepository;
import ru.practicum.ewm_main.user.model.dto.UserMapper;
import ru.practicum.ewm_main.user.model.dto.UserShortDto;
import ru.practicum.ewm_main.user.repository.UserRepository;
import ru.practicum.ewm_main.utility.State;
import ru.practicum.ewm_main.utility.Status;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final EventsRepository eventsRepository;
    private final RequestRepository requestRepository;
    public final CategoriesRepository categoriesRepository;

    public ResponseEntity<Object> getUsersEvents(int userId, int from, int size) {
        List<Events> userEventsList =  eventsRepository.findByInitiatorId(userId,
                                                                          PageRequest.of(from / size, size));
        List<EventShortDto> eventShortDtoList = new ArrayList<>();
        for (Events events : userEventsList) {
            eventShortDtoList.add(EventsMapper.toEventShortDto(events));
        }
        return ResponseEntity.ok(eventShortDtoList);
    }

    @Transactional
    public ResponseEntity<Object> addNewEvents(int userId, NewEventDto dto) {
        Events events = EventsMapper.toEvent(dto, userId);
        events.setState(State.PENDING);
        events.setCreatedOn(LocalDateTime.now());
        eventsRepository.save(events);
        CategoryDto categoryDto = CategoriesMapper.toCategoryDto(categoriesRepository.findById(events.getCategoryId()).get());
        UserShortDto userShortDto = UserMapper.userShortDto(userRepository.findById(events.getInitiatorId()).get());
        EventFullDto fullDto = EventsMapper.toEventFullDto(eventsRepository.findByInitiatorIdAndAnnotation(userId,
                events.getAnnotation()), categoryDto, userShortDto);
        return ResponseEntity.ok(fullDto);
    }

    public ResponseEntity<Object> getUserEventInfo(int userId, int eventId) {
        Events events = eventsRepository.findById(eventId).get();
        if (events.getInitiatorId() != userId) {
            throw new BadRequestException("Некорректный id пользователя");
        }
        CategoryDto categoryDto = CategoriesMapper.toCategoryDto(events.getCategory());
        UserShortDto userShortDto = UserMapper.userShortDto(events.getInitiator());
        EventFullDto shortDto = EventsMapper.toEventFullDto(eventsRepository.findById(eventId).get(),
                categoryDto, userShortDto);
        return ResponseEntity.ok(shortDto);
    }

    @Transactional
    public Object updateUserEvent(int userId, int eventId, UpdateEventUserRequest dto) {
        if (eventsRepository.findById(eventId).isEmpty()) {
            throw new BadRequestException("Не найден");
        }
        Events events = eventsRepository.findById(eventId).get();
        if (events.getState().equals(State.PUBLISHED)) {
            throw new BadRequestException("Событие опубликовано");
        }
        eventsRepository.save(EventsMapper.updateForUsers(events, dto));
        CategoryDto categoryDto = CategoriesMapper.toCategoryDto(events.getCategory());
        UserShortDto userShortDto = UserMapper.userShortDto(events.getInitiator());
        EventFullDto fullDto = EventsMapper.toEventFullDto(eventsRepository.findById(eventId).get(),
                categoryDto, userShortDto);
        return ResponseEntity.ok(fullDto);
    }

    public ResponseEntity<Object> getUserEventRequest(int userId, int eventId) {
        List<Request> requestList = requestRepository.findByEvent(eventId);
        return ResponseEntity.ok(requestList);
    }

    @Transactional
    public ResponseEntity<List<Request>> updateUserEventRequest(int userId,
                                                                int eventId,
                                                                EventRequestStatusUpdateRequest requestStatusUpdateRequest) {
        if (eventsRepository.findById(eventId).isEmpty()) {
            throw new NotFoundException("Не найдено события с таким id");
        }
        Events events = eventsRepository.findById(eventId).get();
        List<Request> requestList = requestRepository.findByEvent(eventId);
        events.setConfirmedRequests(events.getConfirmedRequests() + requestList.size());
        for (Request request : requestList) {
            request.setStatus(Status.CONFIRMED);
        }
        eventsRepository.save(events);
        requestRepository.saveAll(requestList);
        return ResponseEntity.ok(requestRepository.findByEvent(eventId));
    }

    public ResponseEntity<Object> getRequests(int userId) {
        List<Request> requestList = requestRepository.findByRequester(userId);
        List<RequestDto> requestDtoList = new ArrayList<>();
        for (Request request : requestList) {
            requestDtoList.add(RequestMapper.toRequestDto(request));
        }
        return ResponseEntity.ok(requestDtoList);
    }

    @Transactional
    public ResponseEntity<Object> addNewRequests(int userId, int eventId) {
        if (eventsRepository.findById(eventId).isEmpty()) {
            throw new NotFoundException("Не найден");
        }
        Events event = eventsRepository.findById(eventId).get();
        if (requestRepository.findByRequesterAndEvent(userId, eventId).isPresent()) {
            throw new BadRequestException("Такой запрос уже существует");
        }
        if (event.getParticipantLimit() != 0) {
            if (event.getConfirmedRequests() == event.getParticipantLimit()) {
                throw new BadRequestException("Места закончились");
            }
        }
        Request request = Request.builder()
                .created(LocalDateTime.now())
                .event(eventId)
                .requester(userId)
                .build();
        if (event.getParticipantLimit() != 0) {
            if (event.getRequestModeration()) {
                request.setStatus(Status.PENDING);
            } else {
                request.setStatus(Status.CONFIRMED);
                event.setConfirmedRequests(event.getConfirmedRequests() + 1);
                eventsRepository.save(event);
            }
        } else {
            request.setStatus(Status.CONFIRMED);
            event.setConfirmedRequests(event.getConfirmedRequests() + 1);
            eventsRepository.save(event);
        }
        requestRepository.save(request);
        RequestDto dto = RequestMapper.toRequestDto(requestRepository.findByRequesterAndEvent(userId, eventId).get());
        return ResponseEntity.status(HttpStatus.CREATED).body(dto);
    }

    public ResponseEntity<Object> cancelRequests(int userId, int requestId) {
        if (requestRepository.findById(requestId).isEmpty()) {
            throw new BadRequestException("Запрос не найден");
        }
        Request request = requestRepository.findById(requestId).get();
        if (request.getRequester() != userId) {
            throw new BadRequestException("Пользователь не является владельцем запроса");
        }
        request.setStatus(Status.REJECTED);
        requestRepository.save(request);
        return ResponseEntity.ok(request);
    }
}
