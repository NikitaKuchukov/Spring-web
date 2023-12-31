package ru.skypro.lessons.springboot.springweb.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import ru.skypro.lessons.springboot.springweb.entity.Position;

@Data
@NoArgsConstructor
public class EmployeeDto {

    private int id;
    private String name;
    private int salary;
    private Position position;

    public EmployeeDto(int id, String name, int salary, Position position) {
        this.id = id;
        this.name = name;
        this.salary = salary;
        this.position = position;
    }
}
