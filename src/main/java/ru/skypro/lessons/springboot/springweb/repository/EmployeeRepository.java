package ru.skypro.lessons.springboot.springweb.repository;

import org.springframework.stereotype.Repository;
import ru.skypro.lessons.springboot.springweb.model.Employee;

import java.util.List;

@Repository
public interface EmployeeRepository {
    List<Employee> getAll();
}
