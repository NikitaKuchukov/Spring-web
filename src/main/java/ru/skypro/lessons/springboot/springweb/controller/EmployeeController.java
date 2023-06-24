package ru.skypro.lessons.springboot.springweb.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.skypro.lessons.springboot.springweb.dto.EmployeeDto;
import ru.skypro.lessons.springboot.springweb.service.EmployeeService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("employee")
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeService employeeService;

    @GetMapping("salary/max")
    public EmployeeDto findEmployeeWithMaxSalary() {
        return employeeService.findEmployeeWithMaxSalary();
    }

    @GetMapping("salary/min")
    public EmployeeDto findEmployeeWithMinSalary() {
        return employeeService.findEmployeeWithMinSalary();
    }

    @GetMapping("salary/sum")
    public double findSumSalariesOfEmployees() {
        return employeeService.findSumSalariesOfEmployees();
    }

    @GetMapping("salary/aboveAverage")
    public List<EmployeeDto> findEmployeesWithAboveAverageSalaries() {
        return employeeService.findEmployeesWithAboveAverageSalaries();
    }

    @GetMapping("all")
    public List<EmployeeDto> getAll() {
        return employeeService.getAll();
    }

    @PostMapping("employeeList")
    public List<EmployeeDto> createEmployeeList(@RequestBody List<EmployeeDto> employees) {
        return employeeService.createEmployeeList(employees);
    }

    @PutMapping("{id}")
    public void updateEmployeeById(@PathVariable int id, @RequestBody EmployeeDto employeeDto) {
        employeeService.updateEmployeeById(id, employeeDto);
    }

    @GetMapping("{id}")
    public EmployeeDto getEmployeeById(@PathVariable int id) {
        return employeeService.getEmployeeById(id);
    }

    @DeleteMapping("{id}")
    public void deleteEmployeeById(@PathVariable int id) {
        employeeService.deleteEmployeeById(id);
    }

    @GetMapping("salary/higherThan")
    public List<EmployeeDto> getEmployeesWithSalariesHigherThan(@RequestParam int salary) {
        return employeeService.getEmployeesWithSalariesHigherThan(salary);
    }

    @GetMapping("salary/highest")
    public List<EmployeeDto> getEmployeesWithHighestSalaries() {
        return employeeService.getEmployeesWithHighestSalaries();
    }
@GetMapping
    public List<EmployeeDto> getEmployees(@RequestParam(required = false) String position) {
        return employeeService.getEmployees(
                Optional.ofNullable(position)
                        .filter(pos -> !pos.isEmpty())
                        .orElse(null)
        );
    }
    @GetMapping("page")
    public List<EmployeeDto> getEmployeesFromPage(@RequestParam(required = false, defaultValue = "0") int page) {
        return employeeService.getEmployeesFromPage(page);
    }
}
