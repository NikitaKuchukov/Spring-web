package ru.skypro.lessons.springboot.springweb.service;

import ru.skypro.lessons.springboot.springweb.model.Employee;

import java.util.List;

public interface EmployeeService {
    List<Employee> getAll();

    void createEmployeeList(List<Employee> employees);

    Employee updateEmployeeById(int id, String name, int salary);

    Employee getEmployeeById(int id);

    void deleteEmployeeById(int id);

    List<Employee> getEmployeesWithSalaryHigherThan(int salary);
}
