package ru.practicum.ewm_main.admin.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm_main.admin.service.AdminService;
import ru.practicum.ewm_main.categories.model.dto.NewCategoryDto;
import ru.practicum.ewm_main.compilation.model.dto.NewCompilationDto;
import ru.practicum.ewm_main.compilation.model.dto.UpdateCompilationRequest;
import ru.practicum.ewm_main.event.model.dto.UpdateEventAdminRequest;
import ru.practicum.ewm_main.user.model.dto.UserDto;

import java.util.List;

@RestController
@RequestMapping(path = "/admin")
@Slf4j
@RequiredArgsConstructor
public class AdminControllers {
    private final AdminService service;

    @PostMapping("/categories")
    public ResponseEntity<Object> addNewCategories(@RequestBody NewCategoryDto dto) {
        log.info("===============");
        log.info("Добавление новой категории из админки - " + dto);
        log.info("===============");
        return service.addNewCategories(dto);
    }

    @DeleteMapping("/categories/{catId}")
    public ResponseEntity<Object> deleteCategories(@PathVariable int catId) {
        log.info("===============");
        log.info("Удаление категории из админки - " + catId);
        log.info("===============");
        return service.deleteCategories(catId);
    }

    @PatchMapping("/categories/{catId}")
    public ResponseEntity<Object> updateCategories(@PathVariable int catId,
                                                   @RequestBody NewCategoryDto dto) {
        log.info("===============");
        log.info("Обновление категории из админки - " + dto);
        log.info("===============");
        return service.updateCategories(catId, dto);
    }

    @GetMapping("/events")
    public ResponseEntity<Object> getEvents(@RequestParam(required = false) List<Integer> users,
                                            @RequestParam(required = false) List<String> states,
                                            @RequestParam(required = false) List<Integer> categories,
                                            @RequestParam(required = false) String rangeStart,
                                            @RequestParam(required = false) String rangeEnd,
                                            @RequestParam(defaultValue = "0") int from,
                                            @RequestParam(defaultValue = "10") int size){
        log.info("===============");
        log.info("Начало операции по выборке ивентов из админки");
        log.info("===============");
        return service.getEvents(users, states, categories, rangeStart, rangeEnd, from, size);
    }

    @PatchMapping("/events/{eventId}")
    public ResponseEntity<Object> updateEvents(@PathVariable int eventId,
                                               @RequestBody UpdateEventAdminRequest updateEventAdminRequest) {
        log.info("===============");
        log.info("Операция обновления через админку - " + eventId);
        log.info("===============");
        return service.updateEvents(eventId, updateEventAdminRequest);
    }

    @GetMapping("/users")
    public ResponseEntity<Object> getUsers(@RequestParam int userId,
                               @RequestParam(defaultValue = "0") int from,
                               @RequestParam(defaultValue = "10") int size) {
        log.info("===============");
        log.info("Выборка пользователей через админку - " + userId);
        log.info("===============");
        return service.getUsers(userId, from, size);
    }

    @PostMapping("/users")
    public ResponseEntity<Object> addNewUser(@RequestBody UserDto dto) {
        log.info("===============");
        log.info("Добавление нового пользователя из админки - " + dto);
        log.info("===============");
        return service.addNewUser(dto);
    }

    @DeleteMapping("/users/{userId}")
    public ResponseEntity<Object> deleteUser(@PathVariable int userId) {
        log.info("===============");
        log.info("Удаление пользователя из админки - " + userId);
        log.info("===============");
        return service.deleteUser(userId);
    }

    @PostMapping("/compilations")
    public ResponseEntity<Object> addNewCompilation(@RequestBody NewCompilationDto dto) {
        log.info("===============");
        log.info("Добавление новой комплиментации - " + dto);
        log.info("===============");
        return service.addNewCompilation(dto);
    }

    @DeleteMapping("/compilations/{compId}")
    public ResponseEntity<Object> deleteCompilations(@PathVariable int compId) {
        log.info("===============");
        log.info("Удаление комплиментации - " + compId);
        log.info("===============");
        return service.deleteCompilations(compId);
    }

    @PatchMapping("/compilations/{compId}")
    public ResponseEntity<Object> updateCompilation(@PathVariable int compId,
                                  @RequestBody UpdateCompilationRequest updateCompilationRequest) {
        log.info("===============");
        log.info("Обновление комплиментации - " + compId);
        log.info("===============");
        return service.updateCompilation(compId, updateCompilationRequest);
    }
}
