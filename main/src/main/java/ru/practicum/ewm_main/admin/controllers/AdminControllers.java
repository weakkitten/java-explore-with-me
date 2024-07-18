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
        System.out.println("ПРИВЕТ ПРИВЕТ ПРИВЕТ");
        System.out.println("ПРИВЕТ ПРИВЕТ ПРИВЕТ");
        System.out.println("Начинаем добавлять новую категорию - " + dto);
        System.out.println("ПРИВЕТ ПРИВЕТ ПРИВЕТ");
        return service.addNewCategories(dto);
    }

    @DeleteMapping("/categories/{catId}")
    public ResponseEntity<Object> deleteCategories(@PathVariable int catId) {
        return service.deleteCategories(catId);
    }

    @PatchMapping("/categories/{catId}")
    public ResponseEntity<Object> updateCategories(@PathVariable int catId,
                                                   @RequestBody NewCategoryDto dto) {
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
        return service.getEvents(users, states, categories, rangeStart, rangeEnd, from, size);
    }

    @PatchMapping("/events/{eventId}")
    public ResponseEntity<Object> updateEvents(@PathVariable int eventId,
                             UpdateEventAdminRequest updateEventAdminRequest) {
        return service.updateEvents(eventId, updateEventAdminRequest);
    }

    @GetMapping("/users")
    public ResponseEntity<Object> getUsers(@RequestParam int userId,
                               @RequestParam(defaultValue = "0") int from,
                               @RequestParam(defaultValue = "10") int size) {
        return service.getUsers(userId, from, size);
    }

    @PostMapping("/users")
    public ResponseEntity<Object> addNewUser(@RequestBody UserDto dto) {
        System.out.println("ПРИВЕТ ПРИВЕТ ПРИВЕТ");
        System.out.println("ПРИВЕТ ПРИВЕТ ПРИВЕТ");
        System.out.println("Начинаем добавлять нового пользователя - " + dto);
        System.out.println("ПРИВЕТ ПРИВЕТ ПРИВЕТ");
        return service.addNewUser(dto);
    }

    @DeleteMapping("/users/{userId}")
    public ResponseEntity<Object> deleteUser(@PathVariable int userId) {
        return service.deleteUser(userId);
    }

    @PostMapping("/compilations")
    public ResponseEntity<Object> addNewCompilation(@RequestBody NewCompilationDto dto) {
        return service.addNewCompilation(dto);
    }

    @DeleteMapping("/compilations/{compId}")
    public ResponseEntity<Object> deleteCompilations(@PathVariable int compId) {
        return service.deleteCompilations(compId);
    }

    @PatchMapping("/compilations/{compId}")
    public ResponseEntity<Object> updateCompilation(@PathVariable int compId,
                                  @RequestBody UpdateCompilationRequest updateCompilationRequest) {
        return service.updateCompilation(compId, updateCompilationRequest);
    }
}
