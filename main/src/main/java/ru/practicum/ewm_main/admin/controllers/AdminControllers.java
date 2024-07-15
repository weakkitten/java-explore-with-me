package ru.practicum.ewm_main.admin.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.ewm_main.admin.service.AdminService;

@RestController
@RequestMapping(path = "/users")
@Slf4j
@RequiredArgsConstructor
public class AdminControllers {
    private final AdminService service;


}
