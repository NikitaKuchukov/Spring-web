package ru.skypro.lessons.springboot.springweb.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class EmployeeExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(EmployeeExceptionHandler.class);

    @ExceptionHandler(EmployeeNotFoundException.class)
    public ResponseEntity<?> handleNotFound(EmployeeNotFoundException e) {
        logger.error("There is no employee with id: {}", e.getId());
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body("Сотрудник с id = %d не найден".formatted(e.getId()));
    }
    @ExceptionHandler(EmployeeNotValidException.class)
    public ResponseEntity<?> handleBadRequest(EmployeeNotValidException e) {
        logger.error("Data is not valid: {}", e.getEmployee());
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body("Некорректные данные сотрудника: %s".formatted(e.getEmployee()));
    }
}
