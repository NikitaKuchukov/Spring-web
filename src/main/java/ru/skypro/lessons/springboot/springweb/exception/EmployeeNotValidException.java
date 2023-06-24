package ru.skypro.lessons.springboot.springweb.exception;

import lombok.Data;
import ru.skypro.lessons.springboot.springweb.dto.EmployeeDto;

@Data
public class EmployeeNotValidException extends RuntimeException {

    private final EmployeeDto employee;
}
