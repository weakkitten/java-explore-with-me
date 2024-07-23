package ru.practicum.ewm_main.compilation.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm_main.compilation.service.CompilationService;

@RestController
@RequestMapping(path = "/compilations")
@Slf4j
@RequiredArgsConstructor
public class CompilationController {
    private final CompilationService service;

    @GetMapping
    public ResponseEntity<Object> getCompilations(@RequestParam(required = false) Boolean pinned,
                                                  @RequestParam(defaultValue = "0") int from,
                                                  @RequestParam(defaultValue = "10") int size) {
        log.info("===============");
        log.info("Выборка сборок из КомпилКонтроллера");
        log.info("===============");
        return service.getCompilations(pinned, from, size);
    }

    @GetMapping("/{compId}")
    public ResponseEntity<Object> getCompilationsById(@PathVariable int compId) {
        log.info("===============");
        log.info("Выгрузка Сборки по Ид из КомпилКонтроллера - " + compId);
        log.info("===============");
        return service.getCompilationsById(compId);
    }
}
