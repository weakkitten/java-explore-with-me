package ru.practicum.ewm_main.event.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm_main.event.service.EventsService;

import java.util.List;

@RestController
@RequestMapping(path = "/events")
@Slf4j
@RequiredArgsConstructor
public class EventController {
    private final EventsService service;

    @GetMapping
    public ResponseEntity<Object> getEvents(@RequestParam String text,
                                            @RequestParam List<Integer> categories,
                                            @RequestParam boolean paid,
                                            @RequestParam String rangeStart,
                                            @RequestParam String rangeEnd,
                                            @RequestParam boolean onlyAvailable,
                                            @RequestParam String sort,
                                            @RequestParam(defaultValue = "0") int from,
                                            @RequestParam(defaultValue = "10") int size) {
        return service.getEvents(text, categories, paid, rangeStart, rangeEnd, onlyAvailable, sort, from, size);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getEventsById(@PathVariable int id) {
        return service.getEventsById(id);
    }
}
