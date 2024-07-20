package ru.practicum.ewm_main.event.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm_main.event.service.EventsService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.Future;
import java.util.List;

@RestController
@RequestMapping(path = "/events")
@Slf4j
@RequiredArgsConstructor
public class EventController {
    private final EventsService service;

    @GetMapping
    public ResponseEntity<Object> getEvents(@RequestParam(required = false) String text,
                                            @RequestParam(required = false) List<Integer> categories,
                                            @RequestParam(required = false) boolean paid,
                                            @RequestParam(required = false) String rangeStart,
                                            @RequestParam(required = false) String rangeEnd,
                                            @RequestParam(required = false) boolean onlyAvailable,
                                            @RequestParam(required = false) String sort,
                                            @RequestParam(defaultValue = "0") int from,
                                            @RequestParam(defaultValue = "10") int size) {
        log.info("===============");
        log.info("Выгрузка ивентов из ИвентКонтроллера");
        log.info("===============");
        return service.getEvents(text, categories, paid, rangeStart, rangeEnd, onlyAvailable, sort, from, size);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getEventsById(@PathVariable int id, HttpServletRequest request) {
        log.info("===============");
        log.info("Выгрузка события из ИвентКонтроллера - " + id);
        log.info("===============");
        return service.getEventsById(id, request.getRemoteAddr(), request.getRequestURI());
    }
}
