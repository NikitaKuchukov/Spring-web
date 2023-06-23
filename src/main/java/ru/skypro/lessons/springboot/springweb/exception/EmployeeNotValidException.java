package ru.skypro.lessons.springboot.springweb.exception;

import lombok.Data;
import ru.skypro.lessons.springboot.springweb.model.Employee;

@Data
public class EmployeeNotValidException extends RuntimeException {

    private final Employee employee;
}
