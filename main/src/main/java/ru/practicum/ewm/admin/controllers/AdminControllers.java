package ru.practicum.ewm.admin.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.admin.service.AdminService;
import ru.practicum.ewm.categories.model.dto.NewCategoryDto;
import ru.practicum.ewm.compilation.model.dto.NewCompilationDto;
import ru.practicum.ewm.compilation.model.dto.UpdateCompilationRequest;
import ru.practicum.ewm.event.model.dto.UpdateEventAdminRequest;
import ru.practicum.ewm.user.model.dto.NewUserDto;
import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping(path = "/admin")
@Slf4j
@RequiredArgsConstructor
public class AdminControllers {
    private final AdminService service;

    @PostMapping("/categories")
    public ResponseEntity<Object> addNewCategories(@RequestBody @Valid NewCategoryDto dto) {
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
                                                   @RequestBody @Valid NewCategoryDto dto) {
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
                                            @RequestParam(defaultValue = "10") int size) {
        log.info("===============");
        log.info("Начало операции по выборке ивентов из админки");
        log.info("===============");
        return service.getEvents(users, states, categories, rangeStart, rangeEnd, from, size);
    }

    @PatchMapping("/events/{eventId}")
    public ResponseEntity<Object> updateEvents(@PathVariable int eventId,
                                               @RequestBody @Valid UpdateEventAdminRequest updateEventAdminRequest) {
        log.info("===============");
        log.info("Операция обновления через админку - " + eventId);
        log.info("===============");
        return service.updateEvents(eventId, updateEventAdminRequest);
    }

    @GetMapping("/users")
    public ResponseEntity<Object> getUsers(@RequestParam(required = false) List<Integer> ids,
                                           @RequestParam(defaultValue = "0") int from,
                                           @RequestParam(defaultValue = "10") int size) {
        log.info("===============");
        log.info("Выборка пользователей через админку - " + ids);
        log.info("===============");
        return service.getUsers(ids, from, size);
    }

    @PostMapping("/users")
    public ResponseEntity<Object> addNewUser(@RequestBody @Valid NewUserDto dto) {
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
    public ResponseEntity<Object> addNewCompilation(@RequestBody @Valid NewCompilationDto dto) {
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
                                                    @RequestBody @Valid UpdateCompilationRequest updateCompilationRequest) {
        log.info("===============");
        log.info("Обновление комплиментации - " + compId);
        log.info("===============");
        return service.updateCompilation(compId, updateCompilationRequest);
    }
}
