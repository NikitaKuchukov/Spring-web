package ru.skypro.lessons.springboot.springweb.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.skypro.lessons.springboot.springweb.model.Employee;
import ru.skypro.lessons.springboot.springweb.service.EmployeeService;

import java.util.List;

@RestController
@RequestMapping("employee")
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeService employeeService;

    @RequestMapping("getAll")
    public List<Employee> getAll() {
        return employeeService.getAll();
    }

    @RequestMapping("createList")
    public void createEmployeeList(@RequestBody List<Employee> employees) {
        employeeService.createEmployeeList(employees);
    }

    @RequestMapping("update/{id}")
    public Employee updateEmployeeById(@PathVariable int id,
                                       @RequestParam String name,
                                       @RequestParam int salary) {
        return employeeService.updateEmployeeById(id, name, salary);
    }

    @RequestMapping("get/{id}")
    public Employee getEmployeeById(@PathVariable int id) {
        return employeeService.getEmployeeById(id);
    }

    @RequestMapping("delete/{id}")
    public void deleteEmployeeById(@PathVariable int id) {
        employeeService.deleteEmployeeById(id);
    }

    @RequestMapping("salary/higher")
    public List<Employee> getEmployeesWithSalaryHigherThan(@RequestParam int salary) {
        return employeeService.getEmployeesWithSalaryHigherThan(salary);
    }
}
