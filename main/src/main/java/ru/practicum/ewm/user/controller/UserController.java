package ru.practicum.ewm.user.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.comments.model.dto.NewCommentDto;
import ru.practicum.ewm.comments.model.dto.UpdateCommentDto;
import ru.practicum.ewm.event.model.dto.NewEventDto;
import ru.practicum.ewm.event.model.dto.UpdateEventUserRequest;
import ru.practicum.ewm.request.model.EventRequestStatusUpdateRequest;
import ru.practicum.ewm.user.service.UserService;
import javax.validation.Valid;

@Controller
@RequestMapping(path = "/users")
@Slf4j
@RequiredArgsConstructor
public class UserController {
    private final UserService service;

    @GetMapping("/{userId}/events")
    public ResponseEntity<Object> getUserEvents(@PathVariable int userId,
                                                @RequestParam(defaultValue = "0") int from,
                                                @RequestParam(defaultValue = "10") int size) {
        log.info("===============");
        log.info("Поиск сущностей пользователя с id - " + userId);
        log.info("===============");
        return service.getUsersEvents(userId, from, size);
    }

    @PostMapping("/{userId}/events")
    public ResponseEntity<Object> createUserEvents(@PathVariable int userId, @RequestBody @Valid NewEventDto dto) {
        log.info("===============");
        log.info("Начало операции по добавлению ивента из ЮзерКонтроллера");
        log.info("===============");
        return service.addNewEvents(userId, dto);
    }

    @GetMapping("/{userId}/events/{eventId}")
    public ResponseEntity<Object> getUserEventInfo(@PathVariable int userId, @PathVariable int eventId) {
        log.info("===============");
        log.info("Начало выгрузки информации об ивенте - " + eventId);
        log.info("===============");
        return service.getUserEventInfo(userId, eventId);
    }

    @PatchMapping("/{userId}/events/{eventId}")
    public ResponseEntity<Object> updateUserEvent(@PathVariable int userId,
                                  @PathVariable int eventId,
                                  @RequestBody @Valid UpdateEventUserRequest request) {
        log.info("===============");
        log.info("Обновление ивента из ЮзерКонтроллера - " + eventId);
        log.info("===============");
        return service.updateUserEvent(userId, eventId, request);
    }

    @GetMapping("/{userId}/events/{eventId}/requests")
    public ResponseEntity<Object> getUserEventRequest(@PathVariable int userId,
                                                      @PathVariable int eventId) {
        log.info("===============");
        log.info("Выгрузка запросов по ивенту в ЮзерКонтроллера - " + eventId);
        log.info("===============");
        return service.getUserEventRequest(userId, eventId);
    }

    @PatchMapping("/{userId}/events/{eventId}/requests")
    public ResponseEntity<Object> updateUserEventRequest(@PathVariable int userId,
                                                         @PathVariable int eventId,
                                                         @RequestBody @Valid EventRequestStatusUpdateRequest request) {
        log.info("===============");
        log.info("Обновления запросов по ивенту в ЮзерКонтроллера - " + eventId);
        log.info("Запрос - " + request);
        log.info("===============");
        return service.updateUserEventRequest(userId, eventId, request);
    }

    @GetMapping("/{userId}/requests")
    public ResponseEntity<Object> getRequests(@PathVariable int userId) {
        log.info("===============");
        log.info("Начинаем искать запросы пользователя с id - " + userId);
        log.info("===============");
        return service.getRequests(userId);
    }

    @PostMapping("/{userId}/requests")
    public ResponseEntity<Object> addNewRequests(@PathVariable int userId,
                                                 @RequestParam int eventId) {
        log.info("===============");
        log.info("Добавление нового запроса - " + eventId);
        log.info("===============");
        return service.addNewRequests(userId, eventId);
    }

    @PatchMapping("/{userId}/requests/{requestId}/cancel")
    public ResponseEntity<Object> cancelRequests(@PathVariable int userId, @PathVariable int requestId) {
        log.info("===============");
        log.info("Отмена запроса - " + userId);
        log.info("===============");
        return service.cancelRequests(userId, requestId);
    }

    @PostMapping("/{userId}/events/comment")
    public ResponseEntity<Object> addNewComment(@PathVariable int userId,
                                                @RequestBody @Valid NewCommentDto commentDto) {
        log.info("===============");
        log.info("Добавление комментария к ивенту - ");
        log.info("===============");
        return service.addNewComments(userId, commentDto);
    }

    @PatchMapping("/{userId}/events/comment/{commentId}")
    public ResponseEntity<Object> updateComment(@PathVariable int userId,
                                                @PathVariable int commentId,
                                                @RequestBody @Valid UpdateCommentDto commentDto) {
        log.info("===============");
        log.info("Обновления комментария с id - " + commentId);
        log.info("===============");
        return service.updateComments(userId, commentId, commentDto);
    }

    @DeleteMapping("/{userId}/events/comment/{commentId}")
    public ResponseEntity<Object> deleteComment(@PathVariable int userId,
                                                @PathVariable int commentId) {
        log.info("===============");
        log.info("Обновления комментария с id - " + commentId);
        log.info("===============");
        return service.deleteComments(userId, commentId);
    }
}
