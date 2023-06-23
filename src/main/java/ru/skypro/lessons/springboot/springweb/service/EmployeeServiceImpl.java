package ru.skypro.lessons.springboot.springweb.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.skypro.lessons.springboot.springweb.exception.EmployeeNotFoundException;
import ru.skypro.lessons.springboot.springweb.exception.EmployeeNotValidException;
import ru.skypro.lessons.springboot.springweb.model.Employee;
import ru.skypro.lessons.springboot.springweb.repository.EmployeeRepository;

import java.util.List;
import java.util.Optional;
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
    public List<Employee> createEmployeeList(List<Employee> employees) {
        Optional<Employee> incorrectEmployee = employees.stream()
                .filter(employee -> employee.getSalary() < 0 || employee.getName() == null
                        || employee.getName().isEmpty())
                .findFirst();

        if (incorrectEmployee.isPresent()) {
            throw new EmployeeNotValidException(incorrectEmployee.get());
        }
        return employees.stream()
                .map(employee -> new Employee(employee.getName(), employee.getSalary()))
                .map(employeeRepository::add)
                .collect(Collectors.toList());
    }

    @Override
    public Employee updateEmployeeById(int id, String name, int salary) {
        Employee employee = employeeRepository.getAll().get(id);
        if (employee == null) {
            throw new EmployeeNotFoundException(id);
        }
        employee.setName(name);
        employee.setSalary(salary);
        return employee;
    }

    @Override
    public Employee getEmployeeById(int id) {
        List<Employee> employees = employeeRepository.getAll();
        Employee employee = employees.get(id);
        if (employee == null) {
            throw new EmployeeNotFoundException(id);
        } else return employee;
    }

    @Override
    public void deleteEmployeeById(int id) {
        List<Employee> employees = employeeRepository.getAll();
        Employee employee = employees.get(id);
        if (employee == null) {
            throw new EmployeeNotFoundException(id);
        } else employees.remove(employee);
    }

    @Override
    public List<Employee> getEmployeesWithSalaryHigherThan(int salary) {
        return employeeRepository.getAll().stream()
                .filter(employee -> employee.getSalary() > salary)
                .collect(Collectors.toList());
    }
}
