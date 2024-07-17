package ru.practicum.ewm_main.user.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm_main.event.model.dto.NewEventDto;
import ru.practicum.ewm_main.request.model.EventRequestStatusUpdateRequest;
import ru.practicum.ewm_main.request.model.Request;
import ru.practicum.ewm_main.user.service.UserService;

import java.util.List;

@RestController
@RequestMapping(path = "/users")
@Slf4j
@RequiredArgsConstructor
public class UserController {
    private final UserService service;

    @GetMapping("/{userId}/events")
    public ResponseEntity<Object> getUserEvents(@PathVariable int userId,
                                        @RequestParam(defaultValue = "0") int from,
                                        @RequestParam(defaultValue = "10") int size) {
        return service.getUsersEvents(userId, from, size);
    }

    @PostMapping("/{userId}/events")
    public ResponseEntity<Object> createUserEvents(@PathVariable int userId, @RequestBody NewEventDto dto) {
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
    public Object getRequests(@PathVariable int userId) {
        return null;
    }

    @PostMapping("/{userId}/requests")
    public Object addNewRequests(@PathVariable int userId) {
        return null;
    }

    @PatchMapping("/{userId}/requests/{requestId}/cancel")
    public Object cancelRequests(@PathVariable int userId, @PathVariable int requestId) {
        return null;
    }
}
