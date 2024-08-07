package ru.practicum.ewm.categories.contollers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.categories.service.CategoriesService;

@RestController
@RequestMapping(path = "/categories")
@Slf4j
@RequiredArgsConstructor
public class CategoriesController {
    private final CategoriesService service;

    @GetMapping
    public ResponseEntity<Object> getCategories(@RequestParam(defaultValue = "0") int from,
                                                @RequestParam(defaultValue = "10") int size) {
        log.info("===============");
        log.info("Выборка категории из КатегориКонтроллер");
        log.info("===============");
        return service.getCategories(from, size);
    }

    @GetMapping("/{catId}")
    public ResponseEntity<Object> getCategoriesById(@PathVariable int catId) {
        log.info("===============");
        log.info("Поиск по id в КатегориКонтроллер - " + catId);
        log.info("===============");
        return service.getCategoriesById(catId);
    }
}
