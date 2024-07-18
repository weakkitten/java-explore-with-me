package ru.practicum.ewm_main.user.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm_main.event.model.dto.NewEventDto;
import ru.practicum.ewm_main.request.model.EventRequestStatusUpdateRequest;
import ru.practicum.ewm_main.request.model.Request;
import ru.practicum.ewm_main.user.service.UserService;

import javax.validation.Valid;
import java.util.List;

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
        log.info("Поиск сущностей пользователя с id - " + userId);
        return service.getUsersEvents(userId, from, size);
    }

    @PostMapping("/{userId}/events")
    public ResponseEntity<Object> createUserEvents(@PathVariable int userId, @RequestBody @Valid NewEventDto dto) {
        log.info("Начало операции по добавлению сущности - " + dto);
        return service.addNewEvents(userId, dto);
    }

    @GetMapping("/{userId}/events/{eventId}")
    public Object getUserEventInfo(@PathVariable int userId, @PathVariable int eventId) {
        return service.getUserEventInfo(userId, eventId);
    }

    @PatchMapping("/{userId}/events/{eventId}")
    public Object updateUserEvent(@PathVariable int userId, @PathVariable int eventId) {
        return service.updateUserEvent(userId, eventId);
    }

    @GetMapping("/{userId}/events/{eventId}/requests")
    public ResponseEntity<Object> getUserEventRequest(@PathVariable int userId,
                                                             @PathVariable int eventId) {
        return service.getUserEventRequest(userId, eventId);
    }

    @PatchMapping("/{userId}/events/{eventId}/requests")
    public ResponseEntity<List<Request>> updateUserEventRequest(@PathVariable int userId,
                                                                @PathVariable int eventId,
                                                                @RequestBody EventRequestStatusUpdateRequest request) {
        return service.updateUserEventRequest(userId, eventId, request);
    }

    @GetMapping("/{userId}/requests")
    public ResponseEntity<Object> getRequests(@PathVariable int userId) {
        System.out.println("ПРИВЕТ ПРИВЕТ ПРИВЕТ");
        System.out.println("ПРИВЕТ ПРИВЕТ ПРИВЕТ");
        System.out.println("Начинаем искать запросы пользователя с id - " + userId);
        System.out.println("ПРИВЕТ ПРИВЕТ ПРИВЕТ");
        return service.getRequests(userId);
    }

    @PostMapping("/{userId}/requests")
    public ResponseEntity<Object> addNewRequests(@PathVariable int userId,
                                                 @RequestParam int eventId) {
        return service.addNewRequests(userId, eventId);
    }

    @PatchMapping("/{userId}/requests/{requestId}/cancel")
    public ResponseEntity<Object> cancelRequests(@PathVariable int userId, @PathVariable int requestId) {
        return service.cancelRequests(userId, requestId);
    }
}
