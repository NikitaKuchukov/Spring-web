package ru.skypro.lessons.springboot.springweb.service;

import org.springframework.stereotype.Component;
import ru.skypro.lessons.springboot.springweb.dto.EmployeeDto;
import ru.skypro.lessons.springboot.springweb.dto.PositionDto;
import ru.skypro.lessons.springboot.springweb.entity.Employee;
import ru.skypro.lessons.springboot.springweb.entity.Position;

import java.util.Optional;

@Component
public class EmployeeMapper {

    public Employee toEntity(EmployeeDto employeeDto) {
        Employee employee = new Employee();
        employee.setName(employeeDto.getName());
        employee.setSalary(employeeDto.getSalary());

        PositionDto positionDto = employeeDto.getPosition();
        Position position = new Position();
        position.setId(positionDto.getId());
        position.setPosition(positionDto.getName());
        employee.setPosition(position);
        return employee;
    }

    public EmployeeDto toDto(Employee employee) {
        EmployeeDto employeeDTO = new EmployeeDto();
        employeeDTO.setId(employee.getId());
        employeeDTO.setName(employee.getName());
        employeeDTO.setSalary(employee.getSalary());

        Position position = employee.getPosition();
        PositionDto positionDto = new PositionDto(position.getId(), position.getPosition());
        employeeDTO.setPosition(positionDto);
        return employeeDTO;
    }
}
