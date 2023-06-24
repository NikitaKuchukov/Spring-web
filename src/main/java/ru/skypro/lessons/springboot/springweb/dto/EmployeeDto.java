package ru.skypro.lessons.springboot.springweb.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class EmployeeDto {

    private int id;
    private String name;
    private int salary;
    private String position;

    public EmployeeDto(int id, String name, int salary, String position) {
        this.id = id;
        this.name = name;
        this.salary = salary;
        this.position = position;
    }
}
