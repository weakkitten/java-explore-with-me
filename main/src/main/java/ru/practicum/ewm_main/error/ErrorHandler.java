package ru.practicum.ewm_main.error;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.practicum.ewm_main.error.exception.BadRequestException;
import ru.practicum.ewm_main.error.exception.NotFoundException;

@RestControllerAdvice
@Slf4j
public class ErrorHandler {
    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleBadRequestException(final BadRequestException e) {//409
        log.debug("Конфликт данных с БД");
        return new ErrorResponse("Bad request", HttpStatus.CONFLICT, e.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleNotFoundException(final NotFoundException e) {//404
        log.debug("Конфликт данных с БД");
        return new ErrorResponse("Такие данные уже есть в БД", HttpStatus.NOT_FOUND,  e.getMessage());
    }
}