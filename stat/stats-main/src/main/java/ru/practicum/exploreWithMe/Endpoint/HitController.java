package ru.practicum.exploreWithMe.Endpoint;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.exploreWithMe.Dto.Service.StatService;
import ru.practicum.exploreWithMe.Dto.model.ViewStatsInterface;
import ru.practicum.exploreWithMe.Dto.model.Hit;

import java.util.List;


@RestController
@RequestMapping(path = "")
@Slf4j
@RequiredArgsConstructor
public class HitController {
    private final StatService service;

    @PostMapping("/hit")
    public void saveHit(@RequestBody Hit hit) {
        log.info("Объект отправлен: " + hit);
        service.saveHit(hit);
        log.info("Сохранение прошло успешно");
    }

    @GetMapping("/stats")
    public List<ViewStatsInterface> getStat(@RequestParam String start,
                                            @RequestParam String end,
                                            @RequestParam(required = false) List<String> uris,
                                            @RequestParam(defaultValue = "false") boolean unique) {
        return service.getStat(start, end, uris, unique);
    }
}
