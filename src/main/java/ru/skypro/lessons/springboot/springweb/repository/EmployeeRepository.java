package ru.skypro.lessons.springboot.springweb.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.skypro.lessons.springboot.springweb.dto.EmployeeDto;
import ru.skypro.lessons.springboot.springweb.dto.PositionDto;
import ru.skypro.lessons.springboot.springweb.entity.Employee;

import java.util.List;
import java.util.Optional;


public interface EmployeeRepository extends JpaRepository<Employee, Integer> {

    @Query("SELECT SUM(e.salary) FROM Employee e")
    double findSumSalariesOfEmployees();

    @Query("""
            SELECT new ru.skypro.lessons.springboot.springweb.dto.EmployeeDto(e.id, e.name, e.salary, e.position)
            FROM Employee e JOIN FETCH Position p
            WHERE e.salary > (SELECT AVG(e.salary) FROM Employee e)
            """)
    List<EmployeeDto> findEmployeesWithAboveAverageSalaries();

    @Query(value = """
            SELECT * FROM employee
            WHERE salary = (SELECT MIN(salary) FROM employee)
            LIMIT 1
            """,
            nativeQuery = true)
    Optional<Employee> findEmployeeWithMinSalary();

    @Query(value = """
            SELECT * FROM employee
            WHERE salary = (SELECT MAX(salary) FROM employee)
            LIMIT 1
            """,
            nativeQuery = true)
    Optional<Employee> findEmployeeWithMaxSalary();

    List<Employee> findEmployeeBySalaryGreaterThan(int salary);

    List<Employee> findEmployeesByPosition_PositionContainingIgnoreCase(String position);

    @Query("""
            SELECT new ru.skypro.lessons.springboot.springweb.dto.EmployeeDto(e.id, e.name, e.salary, e.position)
            FROM Employee e
            WHERE e.salary = (SELECT MAX(e.salary) FROM Employee e)
            """)
    List<EmployeeDto> findEmployeeWithHighSalary();


}
