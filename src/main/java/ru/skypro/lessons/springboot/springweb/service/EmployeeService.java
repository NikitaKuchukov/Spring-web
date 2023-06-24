package ru.skypro.lessons.springboot.springweb.service;

import org.springframework.web.bind.annotation.RequestParam;
import ru.skypro.lessons.springboot.springweb.dto.EmployeeDto;
import ru.skypro.lessons.springboot.springweb.entity.Employee;

import java.util.List;

public interface EmployeeService {
    EmployeeDto findEmployeeWithMinSalary();
    EmployeeDto findEmployeeWithMaxSalary();
    double findSumSalariesOfEmployees();
    List<EmployeeDto> findEmployeesWithAboveAverageSalaries();
    List<EmployeeDto> getAll();

    List<EmployeeDto> createEmployeeList(List<EmployeeDto> employees);

    void updateEmployeeById(int id, EmployeeDto employeeDto);

    EmployeeDto getEmployeeById(int id);

    void deleteEmployeeById(int id);

    List<EmployeeDto> getEmployeesWithSalariesHigherThan(int salary);

    List<EmployeeDto> getEmployeesWithHighestSalaries();
    List<EmployeeDto> getEmployees(String position);

    List<EmployeeDto> getEmployeesFromPage(int page);
}
