package ru.skypro.lessons.springboot.springweb.constants;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.lessons.springboot.springweb.dto.EmployeeDto;
import ru.skypro.lessons.springboot.springweb.entity.Employee;
import ru.skypro.lessons.springboot.springweb.entity.Position;
import ru.skypro.lessons.springboot.springweb.entity.Report;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Clock;
import java.util.List;

@RequiredArgsConstructor
public class Constants {

    public static final Employee EMPLOYEE = new Employee(1, "Anton", 150_000, new Position(1, "Dev"));
    public static final EmployeeDto EMPLOYEE_DTO = new EmployeeDto(1, "Anton", 150_000, new Position(1, "Dev"));

    public static final Position POSITION1 = new Position(1, "Dev");
    public static final Position POSITION2 = new Position(2, "QA");
    public static final List<Position> POSITION_LIST = List.of(POSITION1, POSITION2);

    public static final List<Employee> EMPLOYEE_LIST = List.of(
            new Employee(1, "Andrey", 125_000, new Position(1, "Dev")),
            new Employee(2, "Dima", 150_000, new Position(2, "QA")),
            new Employee(3, "Gosha", 130_000, new Position(1, "Dev")),
            new Employee(4, "Irina", 200_000, new Position(2, "QA")),
            new Employee(5, "Jenya", 180_000, new Position(2, "QA")),
            new Employee(6, "Egor", 160_000, new Position(1, "Dev")),
            new Employee(7, "Anna", 175_000, new Position(1, "Dev")),
            new Employee(8, "Sveta", 190_000, new Position(1, "Dev"))
    );

    public static Page<Employee> GET_LIST_EMPLOYEE_FROM_PAGE(int page) {
        return new PageImpl<>(EMPLOYEE_LIST, PageRequest.of(page, 10), EMPLOYEE_LIST.size());
    }

    public static final List<EmployeeDto> EMPLOYEE_DTO_LIST = List.of(
            new EmployeeDto(1, "Andrey", 125_000, new Position(1, "Dev")),
            new EmployeeDto(2, "Dima", 150_000, new Position(2, "QA")),
            new EmployeeDto(3, "Gosha", 130_000, new Position(1, "Dev")),
            new EmployeeDto(4, "Irina", 200_000, new Position(2, "QA")),
            new EmployeeDto(5, "Jenya", 180_000, new Position(2, "QA")),
            new EmployeeDto(6, "Egor", 160_000, new Position(1, "Dev")),
            new EmployeeDto(7, "Anna", 175_000, new Position(1, "Dev")),
            new EmployeeDto(8, "Sveta", 190_000, new Position(1, "Dev"))
    );

    public static final Report REPORT = new Report(1, "Report for test", Clock.systemDefaultZone().instant(), "path");

    public static final double SUM_OF_SALARY = EMPLOYEE_DTO_LIST.stream()
            .mapToDouble(EmployeeDto::getSalary)
            .sum();

    public static final List<EmployeeDto> EMPLOYEE_DTO_WITH_ABOVE_AVERAGE_SALARY = EMPLOYEE_DTO_LIST.stream()
            .filter(e -> e.getSalary() > (SUM_OF_SALARY / EMPLOYEE_DTO_LIST.size()))
            .toList();
    public static final List<Employee> EMPLOYEE_WITH_ABOVE_AVERAGE_SALARY = EMPLOYEE_LIST.stream()
            .filter(e -> e.getSalary() > (SUM_OF_SALARY / EMPLOYEE_LIST.size()))
            .toList();


}
