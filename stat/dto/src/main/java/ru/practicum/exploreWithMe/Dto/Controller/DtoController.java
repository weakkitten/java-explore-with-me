package ru.practicum.exploreWithMe.Dto.Controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.practicum.exploreWithMe.Dto.Client.DtoClient;
import ru.practicum.exploreWithMe.Dto.model.Hit;
import ru.practicum.exploreWithMe.Dto.model.dto.HitDto;
import java.util.List;

@RestController
@RequestMapping(path = "")
@Slf4j
@RequiredArgsConstructor
public class DtoController {
    private final DtoClient service;

    @PostMapping("/hit")
    public ResponseEntity<Object> saveHit(@RequestBody Hit hit) {
        log.info("Объект получен: " + hit);
        return service.saveHit(hit);
    }

    @GetMapping("stats")
    public ResponseEntity<Object> getStat(@RequestParam String start,
                                          @RequestParam String end,
                                          @RequestParam(required = false) List<String> uris,
                                          @RequestParam(defaultValue = "false") boolean unique) {
        return service.getStat(start, end, uris, unique);
    }
}
