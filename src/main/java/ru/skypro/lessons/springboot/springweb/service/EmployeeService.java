package ru.skypro.lessons.springboot.springweb.service;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.lessons.springboot.springweb.dto.EmployeeDto;

import java.io.File;
import java.util.List;

public interface EmployeeService {
    EmployeeDto getEmployeeWithMinSalary();
    EmployeeDto getEmployeeWithMaxSalary();
    double getSumSalariesOfEmployees();
    List<EmployeeDto> getEmployeesWithAboveAverageSalaries();
    List<EmployeeDto> getAll();

    List<EmployeeDto> createEmployeeList(List<EmployeeDto> employees);

    void updateEmployeeById(int id, EmployeeDto employeeDto);

    EmployeeDto getEmployeeById(int id);

    void deleteEmployeeById(int id);

    List<EmployeeDto> getEmployeesWithSalariesHigherThan(int salary);

    List<EmployeeDto> getEmployeesByPosition(String position);

    List<EmployeeDto> getEmployeesFromPage(int page);

    List<EmployeeDto> getEmployeesWithHighSalary();

    int generateReport();

    Resource findReport(int id);

    void upload(MultipartFile file);

    File findReportFile(int id);
}
