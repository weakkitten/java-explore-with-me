package ru.practicum.ewm.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.Dto.model.dto.NewHit;
import ru.practicum.ewm.Service.StatService;
import java.util.List;

@RestController
@RequestMapping(path = "")
@Slf4j
@RequiredArgsConstructor
public class HitController {
    private final StatService service;

    @PostMapping("/hit")
    public void saveHit(@RequestBody NewHit hit) {
        log.info("==========================");
        log.info("Объект отправлен: " + hit);
        log.info("==========================");
        service.saveHit(hit);
    }

    @GetMapping("/stats")
    public Object getStat(@RequestParam String start,
                          @RequestParam String end,
                          @RequestParam(required = false) List<String> uris,
                          @RequestParam(defaultValue = "false") boolean unique) {
        log.info("==========================");
        log.info("Запрос данных");
        log.info("==========================");
        return service.getStat(start, end, uris, unique);
    }
}
