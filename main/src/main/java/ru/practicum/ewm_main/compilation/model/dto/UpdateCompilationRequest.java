package ru.practicum.ewm_main.compilation.model.dto;

import java.util.List;

public class UpdateCompilationRequest {
    private List<Integer> events;
    private boolean pinned;
    private String title;
}
