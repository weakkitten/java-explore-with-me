package ru.practicum.ewm_main.admin.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm_main.admin.service.AdminService;
import ru.practicum.ewm_main.categories.model.dto.CategoryDto;
import ru.practicum.ewm_main.categories.model.dto.NewCategoryDto;
import ru.practicum.ewm_main.compilation.model.dto.NewCompilationDto;
import ru.practicum.ewm_main.compilation.model.dto.UpdateCompilationRequest;
import ru.practicum.ewm_main.event.model.dto.UpdateEventAdminRequest;
import ru.practicum.ewm_main.user.model.User;

import java.util.List;

@RestController
@RequestMapping(path = "/admin")
@Slf4j
@RequiredArgsConstructor
public class AdminControllers {
    private final AdminService service;

    @PostMapping("/categories")
    public ResponseEntity<Object> addNewCategories(@RequestBody NewCategoryDto dto) {
        return service.addNewCategories(dto);
    }

    @DeleteMapping("/categories/{catId}")
    public ResponseEntity<Object> deleteCategories(@PathVariable int catId) {
        return service.deleteCategories(catId);
    }

    @PatchMapping("/categories/{catId}")
    public void updateCategories(@PathVariable int catId,
                                 @RequestBody NewCategoryDto dto) {
        service.updateCategories(catId, dto);
    }

    @GetMapping("/events")
    public void getEvents() {

    }

    @PatchMapping("/events/{eventId}")
    public void updateEvents(@PathVariable int eventId,
                             UpdateEventAdminRequest updateEventAdminRequest) {
        service.updateEvents(eventId, updateEventAdminRequest);
    }

    @GetMapping("/users")
    public List<User> getUsers(@RequestParam int userId,
                               @RequestParam(defaultValue = "0") int from,
                               @RequestParam(defaultValue = "10") int size) {
        return service.getUsers(userId, from, size);
    }

    @PostMapping("/users")
    public void addNewUser() {

    }

    @DeleteMapping("/users/{userId}")
    public void deleteUser(@PathVariable int userId) {

    }

    @PostMapping("/compilations")
    public void addNewCompilation(@RequestBody NewCompilationDto dto) {
        service.addNewCompilation(dto);
    }

    @DeleteMapping("/compilations/{compId}")
    public void deleteCompilations(@PathVariable int compId) {

    }

    @PatchMapping("/compilations/{compId}")
    public void updateCompilation(@PathVariable int compId,
                                  @RequestBody UpdateCompilationRequest updateCompilationRequest) {

    }
}
