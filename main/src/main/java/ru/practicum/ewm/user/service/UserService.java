package ru.practicum.ewm.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.ewm.categories.model.dto.CategoriesMapper;
import ru.practicum.ewm.categories.model.dto.CategoryDto;
import ru.practicum.ewm.categories.repository.CategoriesRepository;
import ru.practicum.ewm.comments.model.Comments;
import ru.practicum.ewm.comments.model.dto.CommentDtoRequest;
import ru.practicum.ewm.comments.model.dto.CommentMapper;
import ru.practicum.ewm.comments.model.dto.NewCommentDto;
import ru.practicum.ewm.comments.model.dto.UpdateCommentDto;
import ru.practicum.ewm.comments.repository.CommentRepository;
import ru.practicum.ewm.error.exception.BadRequestException;
import ru.practicum.ewm.error.exception.ConflictException;
import ru.practicum.ewm.error.exception.NotFoundException;
import ru.practicum.ewm.event.model.Events;
import ru.practicum.ewm.event.model.dto.*;
import ru.practicum.ewm.event.repository.EventsRepository;
import ru.practicum.ewm.request.model.*;
import ru.practicum.ewm.request.repository.RequestRepository;
import ru.practicum.ewm.user.model.dto.UserMapper;
import ru.practicum.ewm.user.model.dto.UserShortDto;
import ru.practicum.ewm.user.repository.UserRepository;
import ru.practicum.ewm.utility.State;
import ru.practicum.ewm.utility.Status;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final EventsRepository eventsRepository;
    private final RequestRepository requestRepository;
    public final CategoriesRepository categoriesRepository;
    public final CommentRepository commentRepository;

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
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        if (LocalDateTime.parse(dto.getEventDate(), formatter).isBefore(LocalDateTime.now())) {
            throw new BadRequestException("Некорректное время события");
        }
        events.setState(State.PENDING);
        events.setCreatedOn(LocalDateTime.parse(LocalDateTime.now().format(formatter), formatter));
        eventsRepository.save(events);
        CategoryDto categoryDto = CategoriesMapper.toCategoryDto(categoriesRepository.findById(events.getCategoryId()).get());
        UserShortDto userShortDto = UserMapper.userShortDto(userRepository.findById(events.getInitiatorId()).get());
        EventFullDto fullDto = EventsMapper.toEventFullDto(eventsRepository.findByInitiatorIdAndAnnotation(userId,
                events.getAnnotation()), categoryDto, userShortDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(fullDto);
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
    public ResponseEntity<Object> updateUserEvent(int userId, int eventId, UpdateEventUserRequest dto) {
        if (eventsRepository.findById(eventId).isEmpty()) {
            throw new BadRequestException("Не найден");
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        System.out.println("Время - " + dto.getEventDate());
        if (dto.getEventDate() != null) {
            if (LocalDateTime.parse(dto.getEventDate(), formatter).isBefore(LocalDateTime.now())) {
                throw new BadRequestException("Некорректное время для обновления");
            }
        }
        Events events = eventsRepository.findById(eventId).get();
        if (events.getState().equals(State.PUBLISHED)) {
            throw new ConflictException("Событие опубликовано");
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
        List<ParticipationRequestDto> dto = new ArrayList<>();
        for (Request request : requestList) {
            dto.add(RequestMapper.toRequestDto(request));
        }
        return ResponseEntity.ok(dto);
    }

    @Transactional
    public ResponseEntity<Object> updateUserEventRequest(int userId,
                                                         int eventId,
                                                         EventRequestStatusUpdateRequest requestStatusUpdateRequest) {
        System.out.println("Мы попадаем сюда?");
        if (eventsRepository.findById(eventId).isEmpty()) {
            throw new NotFoundException("Не найдено события с таким id");
        }
        Events events = eventsRepository.findById(eventId).get();
        if (events.getConfirmedRequests() >= events.getParticipantLimit()) {
            throw new ConflictException("Достигнут лимит участников");
        }

        if (events.getParticipantLimit() != 0) {
            if (requestStatusUpdateRequest.getStatus().equals(Status.CONFIRMED)) {
                int availablePlace = events.getParticipantLimit() - events.getConfirmedRequests();
                List<Integer> requestConfId = new ArrayList<>();
                List<Integer> reguestRejId = new ArrayList<>();
                if (availablePlace < requestStatusUpdateRequest.getRequestIds().size()) {
                    for (int i = 0; i < availablePlace; i++) {
                        events.setConfirmedRequests(events.getConfirmedRequests() + 1);
                        requestConfId.add(requestStatusUpdateRequest.getRequestIds().get(i));
                    }
                    for (int i = availablePlace + 1; i < requestStatusUpdateRequest.getRequestIds().size(); i++) {
                        reguestRejId.add(requestStatusUpdateRequest.getRequestIds().get(i));
                    }
                    List<Request> requestListConf = requestRepository.findByIds(requestConfId);
                    List<Request> requestListRej = requestRepository.findByIds(reguestRejId);
                    for (Request request : requestListConf) {
                        request.setStatus(Status.CONFIRMED);
                    }
                    for (Request request : requestListRej) {
                        request.setStatus(Status.REJECTED);
                    }
                    eventsRepository.save(events);
                    requestRepository.saveAll(requestListConf);
                    requestRepository.saveAll(requestListRej);
                } else {
                    List<Request> requestList = requestRepository.findByIds(requestStatusUpdateRequest.getRequestIds());
                    for (Request request : requestList) {
                        request.setStatus(requestStatusUpdateRequest.getStatus());
                    }
                    events.setConfirmedRequests(events.getConfirmedRequests() +
                            requestStatusUpdateRequest.getRequestIds().size());
                    eventsRepository.save(events);
                    requestRepository.saveAll(requestList);
                }
            } else {
                List<Request> requestList = requestRepository.findByIds(requestStatusUpdateRequest.getRequestIds());
                for (Request request : requestList) {
                    request.setStatus(requestStatusUpdateRequest.getStatus());
                }
                events.setConfirmedRequests(events.getConfirmedRequests() +
                        requestStatusUpdateRequest.getRequestIds().size());
                eventsRepository.save(events);
                requestRepository.saveAll(requestList);
            }
        }
        List<Request> requestListConf = requestRepository.findByEventAndStatus(eventId, Status.CONFIRMED);
        List<Request> requestListRej = requestRepository.findByEventAndStatus(eventId, Status.REJECTED);
        List<ParticipationRequestDto> dtoConf = new ArrayList<>();
        List<ParticipationRequestDto> dtoRej = new ArrayList<>();
        for (Request request: requestListConf) {
            dtoConf.add(RequestMapper.toRequestDto(request));
        }
        for (Request request: requestListRej) {
            dtoRej.add(RequestMapper.toRequestDto(request));
        }
        EventRequestStatusUpdateResult result = EventRequestStatusUpdateResult.builder()
                .confirmedRequests(dtoConf)
                .rejectedRequests(dtoRej)
                .build();
        return ResponseEntity.ok(result);
    }

    public ResponseEntity<Object> getRequests(int userId) {
        List<Request> requestList = requestRepository.findByRequester(userId);
        List<ParticipationRequestDto> dto = new ArrayList<>();

        for (Request request : requestList) {
            dto.add(RequestMapper.toRequestDto(request));
        }
        return ResponseEntity.ok(dto);
    }

    @Transactional
    public ResponseEntity<Object> addNewRequests(int userId, int eventId) {
        if (eventsRepository.findById(eventId).isEmpty()) {
            throw new NotFoundException("Не найден");
        }
        Events event = eventsRepository.findById(eventId).get();
        if (event.getState().equals(State.PENDING)) {
            throw new ConflictException("Такой ивент еще не опубликован");
        }
        if (requestRepository.findByRequesterAndEvent(userId, eventId).isPresent()) {
            throw new ConflictException("Такой запрос уже существует");
        }
        if (event.getParticipantLimit() != 0) {
            if (event.getConfirmedRequests() == event.getParticipantLimit()) {
                throw new ConflictException("Места закончились");
            }
        }
        if (event.getInitiatorId() == userId) {
            throw new ConflictException("Инициатор подает заявку");
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
        ParticipationRequestDto dto = RequestMapper
                .toRequestDto(requestRepository.findByRequesterAndEvent(userId, eventId).get());
        return ResponseEntity.status(HttpStatus.CREATED).body(dto);
    }

    @Transactional
    public ResponseEntity<Object> cancelRequests(int userId, int requestId) {
        if (requestRepository.findById(requestId).isEmpty()) {
            throw new BadRequestException("Запрос не найден");
        }
        Request request = requestRepository.findById(requestId).get();
        if (request.getRequester() != userId) {
            throw new BadRequestException("Пользователь не является владельцем запроса");
        }
        request.setStatus(Status.CANCELED);
        requestRepository.save(request);
        ParticipationRequestDto requestDto = RequestMapper.toRequestDto(request);
        return ResponseEntity.ok(requestDto);
    }

    @Transactional
    public ResponseEntity<Object> addNewComments(int userId, NewCommentDto dto) {
        Comments comments = CommentMapper.toComment(dto, userId);
        commentRepository.save(comments);
        Comments temp = commentRepository.findByUserIdAndEventIdAndText(userId, dto.getEventId(), dto.getText());
        CommentDtoRequest dtoRequest = CommentMapper.toCommentRequest(temp);
        return ResponseEntity.status(HttpStatus.CREATED).body(dtoRequest);
    }

    @Transactional
    public ResponseEntity<Object> updateComments(int userId, int commentId, UpdateCommentDto commentDto) {
        if (commentRepository.findById(commentId).isEmpty()) {
            throw new BadRequestException("Ошибка поиска комментария");
        }
        Comments comments = commentRepository.findById(commentId).get();
        if (commentDto.getText().equals(comments.getText())) {
            throw new ConflictException("Одинаковый текст");
        }
        if (commentDto.getEventId() != null) {
            if (comments.getEventId() != commentDto.getEventId()) {
                throw new ConflictException("Некорректный eventId");
            }
        }
        if (comments.getUserId() != userId) {
            throw new ConflictException("Этот пользователь не может изменить комментарий");
        }
        comments.setText(commentDto.getText());
        commentRepository.save(comments);
        CommentDtoRequest dtoRequest = CommentMapper.toCommentRequest(comments);
        return ResponseEntity.ok(dtoRequest);
    }

    @Transactional
    public ResponseEntity<Object> deleteComments(int userId, int commentId) {
        if (commentRepository.findById(commentId).isEmpty()) {
            throw new BadRequestException("Комментария с таким id не найден");
        }
        Comments comments = commentRepository.findById(commentId).get();
        if (comments.getUserId() != userId) {
            throw new ConflictException("Этот пользователь не может удалить комментарий");
        }
        categoriesRepository.deleteById(commentId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
    }
}