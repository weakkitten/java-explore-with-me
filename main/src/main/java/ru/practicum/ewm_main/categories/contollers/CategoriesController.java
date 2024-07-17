package ru.practicum.ewm_main.categories.contollers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm_main.categories.service.CategoriesService;

@RestController
@RequestMapping(path = "/categories")
@Slf4j
@RequiredArgsConstructor
public class CategoriesController {
    private final CategoriesService service;

    @GetMapping
    public ResponseEntity<Object> getCategories(@RequestParam(defaultValue = "0") int from,
                                                @RequestParam(defaultValue = "10") int size) {
        return service.getCategories(from, size);
    }

    @GetMapping("/{}catId")
    public ResponseEntity<Object> getCategoriesById(@PathVariable int catId) {
        return service.getCategoriesById(catId);
    }
}
