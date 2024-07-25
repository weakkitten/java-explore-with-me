package ru.practicum.exploreWithMe.error;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
@Getter
public class ErrorResponse {
    private final String error;
    private final HttpStatus status;
    private final String description;
}