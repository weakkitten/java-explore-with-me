package ru.practicum.ewm_main.error;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.practicum.ewm_main.error.exception.BadRequestException;

@RestControllerAdvice
@Slf4j
public class ErrorHandler {
    @ExceptionHandler
    @ResponseStatus
    public ErrorResponse handleBadRequestException(final BadRequestException e) {
        log.debug("Конфликт данных с БД");
        return new ErrorResponse("Такие данные уже есть в БД", e.getMessage());
    }
}