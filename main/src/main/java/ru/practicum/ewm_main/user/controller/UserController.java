package ru.practicum.ewm_main.user.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm_main.user.service.UserService;

@RestController
@RequestMapping(path = "/users")
@Slf4j
@RequiredArgsConstructor
public class UserController {
    private final UserService service;

    @GetMapping("/{userId}/events")
    public Object getUserEvents(@PathVariable int userId) {
        return null;
    }

    @PostMapping("/{userId}/events")
    public Object createUserEvents(@PathVariable int userId) {
        return null;
    }

    @GetMapping("/{userId}/events/{eventId}")
    public Object getUserEventInfo(@PathVariable int userId, @PathVariable int eventId) {
        return null;
    }

    @PatchMapping("/{userId}/events/{eventId}")
    public Object updateUserEvent(@PathVariable int userId, @PathVariable int eventId) {
        return null;
    }

    @GetMapping("/{userId}/events/{eventId}/requests")
    public Object getUserEventRequest(@PathVariable int userId, @PathVariable int eventId) {
        return null;
    }

    @PatchMapping("/{userId}/events/{eventId}/requests")
    public Object updateUserEventRequest(@PathVariable int userId, @PathVariable int eventId) {
        return null;
    }
}
