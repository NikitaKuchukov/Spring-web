package ru.skypro.lessons.springboot.springweb.exception;

import lombok.Data;

@Data
public class EmployeeNotFoundException extends RuntimeException {

    private final int id;
}
