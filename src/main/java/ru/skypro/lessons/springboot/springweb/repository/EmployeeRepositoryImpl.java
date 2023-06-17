package ru.skypro.lessons.springboot.springweb.repository;

import org.springframework.stereotype.Component;
import ru.skypro.lessons.springboot.springweb.model.Employee;

import java.util.ArrayList;
import java.util.List;

@Component
public class EmployeeRepositoryImpl implements EmployeeRepository {
    private final List<Employee> EMPLOYEE_LIST = List.of(
            new Employee("Anton", 150000),
            new Employee("Gena", 110000),
            new Employee("Jenya", 120000),
            new Employee("Katya", 90000),
            new Employee("Luba", 210000),
            new Employee("Vera", 180000),
            new Employee("Liza", 220000),
            new Employee("Dima", 170000),
            new Employee("Vova", 120000)
    );

    @Override
    public List<Employee> getAll() {
        return new ArrayList<>(EMPLOYEE_LIST);
    }
}
