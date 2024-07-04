package ru.practicum.exploreWithMe.Endpoint;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.practicum.exploreWithMe.Endpoint.Client.HitClient;
import ru.practicum.exploreWithMe.Endpoint.model.Hit;

import java.util.List;


@RestController
@RequestMapping(path = "")
@Slf4j
@RequiredArgsConstructor
public class HitController {
    private final HitClient service;

    @PostMapping("/hit")
    public ResponseEntity<Object> saveHit(@RequestBody Hit hit) {
        log.info("Объект отправлен: " + hit);
        return service.saveHit(hit);
    }

    @GetMapping("/stats")
    public ResponseEntity<Object> getStat(@RequestParam String start,
                                          @RequestParam String end,
                                          @RequestParam(required = false) List<String> uris,
                                          @RequestParam(defaultValue = "false") boolean unique) {
        return service.getStat(start, end, uris, unique);
    }
}
