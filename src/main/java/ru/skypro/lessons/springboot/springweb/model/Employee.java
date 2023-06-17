package ru.skypro.lessons.springboot.springweb.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class Employee {

    private static int id;
    private String name;
    private int salary;

    public Employee(String name, int salary) {
        id++;
        this.name = name;
        this.salary = salary;

    }
}
