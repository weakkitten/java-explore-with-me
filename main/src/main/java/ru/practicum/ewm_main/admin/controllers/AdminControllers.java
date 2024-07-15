package ru.practicum.ewm_main.admin.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm_main.admin.service.AdminService;

@RestController
@RequestMapping(path = "/admin")
@Slf4j
@RequiredArgsConstructor
public class AdminControllers {
    private final AdminService service;

    @PostMapping("/categories")
    public void addNewCategories() {

    }

    @DeleteMapping("/categories/{catId}")
    public void deleteCategories(@PathVariable int catId) {

    }

    @PatchMapping("/categories/{catId}")
    public void updateCategories(@PathVariable int catId) {

    }

    @GetMapping("/events")
    public void getEvents() {

    }

    @PatchMapping("/events/{eventId}")
    public void updateEvents(@PathVariable int eventId) {

    }

    @GetMapping("/users")
    public void getUsers() {

    }

    @PostMapping("/users")
    public void addNewUser() {

    }

    @DeleteMapping("/users/{userId}")
    public void deleteUser(@PathVariable int userId) {

    }

    @PostMapping("/compilations")
    public void addNewCompilation() {

    }

    @DeleteMapping("/compilations/{compId}")
    public void deleteCompilations(@PathVariable int compId) {

    }

    @PatchMapping("/compilations/{compId}")
    public void updateCompilation() {

    }
}
