package ru.skypro.lessons.springboot.springweb.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.skypro.lessons.springboot.springweb.model.Employee;
import ru.skypro.lessons.springboot.springweb.repository.EmployeeRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;

    @Override
    public List<Employee> getAll() {
        return employeeRepository.getAll();
    }

    @Override
    public void createEmployeeList(List<Employee> employees) {
     employeeRepository.getAll().addAll(employees);
    }

    @Override
    public Employee updateEmployeeById(int id, String name, int salary) {
        Employee employee = employeeRepository.getAll().get(id);
        employee.setName(name);
        employee.setSalary(salary);
        return employee;
    }

    @Override
    public Employee getEmployeeById(int id) {
        return employeeRepository.getAll().get(id);
    }

    @Override
    public void deleteEmployeeById(int id) {
        employeeRepository.getAll().remove(id);
    }

    @Override
    public List<Employee> getEmployeesWithSalaryHigherThan(int salary) {
        return employeeRepository.getAll().stream()
                .filter(employee -> employee.getSalary() > salary)
                .collect(Collectors.toList());
    }
}
