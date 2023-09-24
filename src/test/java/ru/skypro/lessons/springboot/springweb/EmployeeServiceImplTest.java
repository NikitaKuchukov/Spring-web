package ru.skypro.lessons.springboot.springweb;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.lessons.springboot.springweb.dto.EmployeeDto;
import ru.skypro.lessons.springboot.springweb.entity.Employee;
import ru.skypro.lessons.springboot.springweb.exception.EmployeeNotFoundException;
import ru.skypro.lessons.springboot.springweb.mapper.EmployeeMapper;
import ru.skypro.lessons.springboot.springweb.repository.EmployeeRepository;
import ru.skypro.lessons.springboot.springweb.repository.ReportRepository;
import ru.skypro.lessons.springboot.springweb.service.EmployeeServiceImpl;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static ru.skypro.lessons.springboot.springweb.constants.Constants.*;

@ExtendWith(MockitoExtension.class)
public class EmployeeServiceImplTest {
    @Mock
    private EmployeeRepository mockedRepository;
    @Mock
    private EmployeeMapper mockedMapper;
    @Mock
    private ReportRepository mockedReportRepository;
    @InjectMocks
    private EmployeeServiceImpl out;

    @Test
    @DisplayName("Get Employee with min Salary. Positive result")
    void getEmployeeWithMinSalary_OK() {
        when(mockedRepository.findEmployeeWithMinSalary())
                .thenReturn(Optional.of(EMPLOYEE));
        when(mockedMapper.toDto(any(Employee.class)))
                .thenReturn(EMPLOYEE_DTO);

        assertEquals(EMPLOYEE_DTO, out.getEmployeeWithMinSalary());
        verify(mockedRepository, times(1)).findEmployeeWithMinSalary();
        verify(mockedMapper, times(1)).toDto(any(Employee.class));

    }

    @Test
    @DisplayName("Get Employee with max Salary. Positive result")
    void getEmployeeWithMaxSalary_OK() {
        when(mockedRepository.findEmployeeWithMinSalary())
                .thenReturn(Optional.of(EMPLOYEE));
        when(mockedMapper.toDto(any(Employee.class)))
                .thenReturn(EMPLOYEE_DTO);

        assertEquals(EMPLOYEE_DTO, out.getEmployeeWithMinSalary());
        verify(mockedRepository, times(1)).findEmployeeWithMinSalary();
        verify(mockedMapper, times(1)).toDto(any(Employee.class));

    }

    @Test
    @DisplayName("Get sum Salaries of Employees. Positive result")
    void getSumSalariesOfEmployees_OK() {
        when(mockedRepository.findSumSalariesOfEmployees())
                .thenReturn(SUM_OF_SALARY);

        assertEquals(SUM_OF_SALARY, out.getSumSalariesOfEmployees());
        verify(mockedRepository, times(1)).findSumSalariesOfEmployees();
    }

    @Test
    @DisplayName("Get Employees with above average Salaries. Positive result")
    void getEmployeesWithAboveAverageSalaries_OK() {
        when(mockedRepository.findEmployeesWithAboveAverageSalaries())
                .thenReturn(EMPLOYEE_DTO_WITH_ABOVE_AVERAGE_SALARY);

        assertEquals(EMPLOYEE_WITH_ABOVE_AVERAGE_SALARY, out.getEmployeesWithAboveAverageSalaries());
        verify(mockedRepository, times(1)).findEmployeesWithAboveAverageSalaries();
    }

    @Test
    @DisplayName("Get all Employees. Positive result")
    void getAll_OK() {
        when(mockedRepository.findAll())
                .thenReturn(EMPLOYEE_LIST);
        when(mockedMapper.toDto(any(Employee.class)))
                .thenAnswer(x ->
                {
                    Employee employee = x.getArgument(0);
                    EmployeeDto employeeDto = new EmployeeDto();
                    employeeDto.setId(employee.getId());
                    employeeDto.setName(employee.getName());
                    employeeDto.setSalary(employee.getSalary());
                    employeeDto.setPosition(employee.getPosition());
                    return employeeDto;
                });

        assertIterableEquals(EMPLOYEE_DTO_LIST, out.getAll());
        verify(mockedRepository, times(1)).findAll();
        verify(mockedMapper, times(EMPLOYEE_LIST.size())).toDto(any(Employee.class));

    }

    @Test
    @DisplayName("Update Employee by id. Positive result")
    void updateEmployeeById_OK() {

        final int id = 3;

        when(mockedRepository.findById(anyInt()))
                .thenReturn(Optional.of(EMPLOYEE_LIST.get(id)));

        out.updateEmployeeById(id, EMPLOYEE_DTO);

        assertEquals(EMPLOYEE_DTO.getName(), EMPLOYEE_LIST.get(id).getName());
        assertEquals(EMPLOYEE_DTO.getSalary(), EMPLOYEE_LIST.get(id).getSalary());
        assertEquals(EMPLOYEE_DTO.getPosition(), EMPLOYEE_LIST.get(id).getPosition());
        verify(mockedRepository, times(1)).findById(id);
        verify(mockedRepository, times(1)).save(EMPLOYEE_LIST.get(3));
    }

    @Test
    @DisplayName("Update Employee by id. Exception")
    void updateEmployeeById_Exception() {
        when(mockedRepository.findById(anyInt()))
                .thenThrow(EmployeeNotFoundException.class);

        assertThrows(EmployeeNotFoundException.class, () -> out.updateEmployeeById(anyInt(), EMPLOYEE_DTO));
        verify(mockedRepository, times(1)).findById(anyInt());
    }

    @Test
    @DisplayName("Get Employee by id. Positive result")
    void getEmployeeById_OK() {
        when(mockedRepository.findById(anyInt()))
                .thenReturn(Optional.of(EMPLOYEE));
        when(mockedMapper.toDto(any(Employee.class)))
                .thenReturn(EMPLOYEE_DTO);

        assertEquals(EMPLOYEE_DTO, out.getEmployeeById(anyInt()));
        verify(mockedRepository, times(1)).findById(anyInt());
        verify(mockedMapper, times(1)).toDto(any(Employee.class));

    }

    @Test
    @DisplayName("Get Employee by id. Exception")
    void getEmployeeById_Exception() {
        when(mockedRepository.findById(anyInt()))
                .thenThrow(EmployeeNotFoundException.class);

        assertThrows(EmployeeNotFoundException.class, () -> out.getEmployeeById(anyInt()));
        verify(mockedRepository, times(1)).findById(anyInt());
    }

    @Test
    @DisplayName("Delete Employee by id. Positive result")
    void deleteEmployeeById_OK() {
        when(mockedRepository.findById(anyInt()))
                .thenReturn(Optional.of(EMPLOYEE));

        out.deleteEmployeeById(anyInt());

        verify(mockedRepository, times(1)).findById(anyInt());
        verify(mockedRepository, times(1)).delete(EMPLOYEE);
    }

    @Test
    @DisplayName("Delete Employee by id. Exception")
    void deleteEmployeeById_Exception() {
        when(mockedRepository.findById(anyInt()))
                .thenThrow(EmployeeNotFoundException.class);

        assertThrows(EmployeeNotFoundException.class, () -> out.getEmployeeById(anyInt()));
        verify(mockedRepository, times(1)).findById(anyInt());
    }

    @Test
    @DisplayName("Get Employee list with Salary higher than any value. Positive result")
    void getEmployeesWithSalaryHigherThan_OK() {
        when(mockedRepository.findEmployeeBySalaryGreaterThan(anyInt()))
                .thenReturn(EMPLOYEE_LIST);
        when(mockedMapper.toDto(any(Employee.class)))
                .thenAnswer(x ->
                {
                    Employee employee = x.getArgument(0);
                    EmployeeDto employeeDto = new EmployeeDto();
                    employeeDto.setId(employee.getId());
                    employeeDto.setName(employee.getName());
                    employeeDto.setSalary(employee.getSalary());
                    employeeDto.setPosition(employee.getPosition());
                    return employeeDto;
                });

        assertIterableEquals(EMPLOYEE_DTO_LIST, out.getEmployeesWithSalariesHigherThan(anyInt()));
        verify(mockedRepository, times(1)).findEmployeeBySalaryGreaterThan(anyInt());
        verify(mockedMapper, times(EMPLOYEE_LIST.size())).toDto(any(Employee.class));

    }

    @Test
    @DisplayName("Get Employees by Position. Positive result")
    void getEmployeeByPosition_OK() {
        when(mockedRepository.findEmployeesByPosition_PositionContainingIgnoreCase(anyString()))
                .thenReturn(EMPLOYEE_LIST);
        when(mockedMapper.toDto(any(Employee.class)))
                .thenAnswer(x ->
                {
                    Employee employee = x.getArgument(0);
                    EmployeeDto employeeDto = new EmployeeDto();
                    employeeDto.setId(employee.getId());
                    employeeDto.setName(employee.getName());
                    employeeDto.setSalary(employee.getSalary());
                    employeeDto.setPosition(employee.getPosition());
                    return employeeDto;
                });

        assertIterableEquals(EMPLOYEE_DTO_LIST, out.getEmployeesByPosition(anyString()));
        verify(mockedRepository, times(1)).findEmployeesByPosition_PositionContainingIgnoreCase(anyString());
        verify(mockedMapper, times(EMPLOYEE_LIST.size())).toDto(any(Employee.class));
    }

    @Test
    @DisplayName("Get Employees by Position. Null Position passed")
    void getEmployeeByPosition_PositionIsNull() {
        when(mockedRepository.findAll())
                .thenReturn(EMPLOYEE_LIST);
        when(mockedMapper.toDto(any(Employee.class)))
                .thenAnswer(x ->
                {
                    Employee employee = x.getArgument(0);
                    EmployeeDto employeeDto = new EmployeeDto();
                    employeeDto.setId(employee.getId());
                    employeeDto.setName(employee.getName());
                    employeeDto.setSalary(employee.getSalary());
                    employeeDto.setPosition(employee.getPosition());
                    return employeeDto;
                });

        assertIterableEquals(EMPLOYEE_DTO_LIST, out.getEmployeesByPosition(null));
        verify(mockedRepository, times(0)).findEmployeesByPosition_PositionContainingIgnoreCase(anyString());
        verify(mockedRepository, times(1)).findAll();
        verify(mockedMapper, times(EMPLOYEE_LIST.size())).toDto(any(Employee.class));
    }

    @Test
    @DisplayName("Get Employee list from page. Positive result")
    void getEmployeesFromPage_OK() {
        final int page = 8;
        when(mockedRepository.findAll(PageRequest.of(page, 10)))
                .thenReturn(GET_LIST_EMPLOYEE_FROM_PAGE(page));
        when(mockedMapper.toDto(any(Employee.class)))
                .thenAnswer(x ->
                {
                    Employee employee = x.getArgument(0);
                    EmployeeDto employeeDto = new EmployeeDto();
                    employeeDto.setId(employee.getId());
                    employeeDto.setName(employee.getName());
                    employeeDto.setSalary(employee.getSalary());
                    employeeDto.setPosition(employee.getPosition());
                    return employeeDto;
                });

        assertEquals(EMPLOYEE_DTO_LIST.size(), out.getEmployeesFromPage(page).size());
        verify(mockedRepository, times(1)).findAll(PageRequest.of(page, 10));
        verify(mockedMapper, times(EMPLOYEE_LIST.size())).toDto(any(Employee.class));
    }

    @Test
    @DisplayName("Get Employees with highest Salary. Positive result")
    void getEmployeesWithHighSalary_OK() {
        when(mockedRepository.findEmployeeWithHighSalary())
                .thenReturn(EMPLOYEE_DTO_LIST);

        assertIterableEquals(EMPLOYEE_DTO_LIST, out.getEmployeesWithHighSalary());
        verify(mockedRepository, times(1)).findEmployeeWithHighSalary();
    }

    @Test
    @DisplayName("Find Report by id. Positive result")
    @SneakyThrows
    void findReport_OK() {
        when(mockedReportRepository.findById(anyInt()))
                .thenReturn(Optional.of(REPORT));

        assertEquals(REPORT.getReport(), new String(out.findReport(anyInt()).getInputStream().readAllBytes(), StandardCharsets.UTF_8));
        verify(mockedReportRepository, times(1)).findById(anyInt());
    }

    @Test
    @DisplayName("Find Report by id. Exception")
    void findReport_Exception() {
        when(mockedReportRepository.findById(anyInt()))
                .thenThrow(IllegalStateException.class);

        assertThrows(IllegalStateException.class, () -> out.findReport(anyInt()));
        verify(mockedReportRepository, times(1)).findById(anyInt());
    }

}
