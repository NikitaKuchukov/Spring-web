package ru.skypro.lessons.springboot.springweb.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.Nullable;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.lessons.springboot.springweb.dto.EmployeeDto;
import ru.skypro.lessons.springboot.springweb.dto.ReportDto;
import ru.skypro.lessons.springboot.springweb.entity.Report;
import ru.skypro.lessons.springboot.springweb.exception.EmployeeNotFoundException;
import ru.skypro.lessons.springboot.springweb.exception.EmployeeNotValidException;
import ru.skypro.lessons.springboot.springweb.entity.Employee;
import ru.skypro.lessons.springboot.springweb.mapper.EmployeeMapper;
import ru.skypro.lessons.springboot.springweb.repository.EmployeeRepository;
import ru.skypro.lessons.springboot.springweb.repository.ReportRepository;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private static final Logger logger = LoggerFactory.getLogger(EmployeeServiceImpl.class);

    private final EmployeeRepository employeeRepository;
    private final EmployeeMapper employeeMapper;
    private final ObjectMapper objectMapper;
    private final ReportRepository reportRepository;

    @Override
    public EmployeeDto findEmployeeWithMinSalary() {
        logger.info("Was invoked method for find employee with min salary");
        return employeeRepository.findEmployeeWithMinSalary()
                .map(employeeMapper::toDto)
                .orElse(null);
    }

    @Override
    public EmployeeDto findEmployeeWithMaxSalary() {
        logger.info("Was invoked method for find employee with max salary");
        return employeeRepository.findEmployeeWithMaxSalary()
                .map(employeeMapper::toDto)
                .orElse(null);
    }

    @Override
    public double findSumSalariesOfEmployees() {
        logger.info("Was invoked method for find sum salaries");
        return employeeRepository.findSumSalariesOfEmployees();
    }

    @Override
    public List<EmployeeDto> findEmployeesWithAboveAverageSalaries() {
        logger.info("Was invoked method for find employees with above average salaries");
        return employeeRepository.findEmployeesWithAboveAverageSalaries();
    }

    @Override
    public List<EmployeeDto> getAll() {
        logger.info("Was invoked method for get all employees");
        return employeeRepository.findAll().stream()
                .map(employeeMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<EmployeeDto> createEmployeeList(List<EmployeeDto> employees) {
        logger.info("Was invoked method for create employee list: {}", employees);
        Optional<EmployeeDto> incorrectEmployee = employees.stream()
                .filter(employee -> employee.getSalary() < 0 || employee.getName() == null
                        || employee.getName().isEmpty())
                .findFirst();

        if (incorrectEmployee.isPresent()) {
            throw new EmployeeNotValidException(incorrectEmployee.get());
        }
        return employeeRepository.saveAll(
                        employees.stream()
                                .map(employeeMapper::toEntity)
                                .collect(Collectors.toList()))
                .stream()
                .map(employeeMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public void updateEmployeeById(int id, EmployeeDto employeeDto) {
        logger.info("Was invoked method for update employee: {} with id: {}", employeeDto, id);
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new EmployeeNotFoundException(id));
        employee.setName(employeeDto.getName());
        employee.setSalary(employeeDto.getSalary());
        employee.setPosition(employeeDto.getPosition());
        employeeRepository.save(employee);
    }

    @Override
    public EmployeeDto getEmployeeById(int id) {
        logger.info("Was invoked method for get employee by id: {}", id);
        return employeeRepository.findById(id)
                .map(employeeMapper::toDto)
                .orElseThrow(() -> new EmployeeNotFoundException(id));

    }

    @Override
    public void deleteEmployeeById(int id) {
        logger.info("Was invoked method for delete employee by id: {}", id);
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new EmployeeNotFoundException(id));
        employeeRepository.delete(employee);
    }

    @Override
    public List<EmployeeDto> getEmployeesWithSalariesHigherThan(int salary) {
        logger.info("Was invoked method for get employees with salary higher than: {}", salary);
        return employeeRepository.findEmployeeBySalaryGreaterThan(salary).stream()
                .map(employeeMapper::toDto)
                .toList();
    }


    @Override
    public List<EmployeeDto> getEmployeesByPosition(@Nullable String position) {
        logger.info("Was invoked method for get employees by position: {}", position);
        return Optional.ofNullable(position)
                .map(employeeRepository::findEmployeesByPosition_PositionContainingIgnoreCase)
                .orElseGet(employeeRepository::findAll)
                .stream()
                .map(employeeMapper::toDto)
                .toList();
    }

    @Override
    public List<EmployeeDto> getEmployeesFromPage(int page) {
        logger.info("Was invoked method for get employees from page: {}", page);
        return employeeRepository.findAll(PageRequest.of(page, 10)).stream()
                .map(employeeMapper::toDto)
                .toList();
    }

    @Override
    public List<EmployeeDto> findEmployeesWithHighSalary() {
        logger.info("Was invoked method for find employees with the highest salary");
        return employeeRepository.findEmployeeWithHighSalary();

    }

    @Override
    public Resource findReport(int id) {
        logger.info("Was invoked method for find report by id: {}", id);
        return new ByteArrayResource(
                reportRepository.findById(id)
                        .orElseThrow(() -> new IllegalStateException("Report with id = %d not found".formatted(id)))
                        .getReport()
                        .getBytes(StandardCharsets.UTF_8));
    }

    @Override
    public void upload(MultipartFile file) {
        logger.info("Was invoked method for upload file with employees in DB: {}", file);
        try {
            List<EmployeeDto> empDto = objectMapper.readValue(file.getBytes(), new TypeReference<>() {
            });
            empDto.stream()
                    .map(employeeMapper::toEntity)
                    .forEach(employeeRepository::save);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public File findReportFile(int id) {
        logger.info("Was invoked method for find report file by id: {}", id);
        return reportRepository.findById(id)
                .map(Report::getPath)
                .map(File::new)
                .orElse(null);
    }

    public String generateReportFile(String content) {
        logger.info("Was invoked method for generate report file");
        File f = new File("report_" + System.currentTimeMillis() + ".json");
        try (Writer writer = new FileWriter(f)) {
            writer.write(content);
        } catch (IOException e) {
            logger.error("Cannot generate report file", e);
            throw new UncheckedIOException("Cannot generate report file", e);
        }
        return f.getName();
    }

    @Override
    public int generateReport() {
        logger.info("Was invoked method for generate report");
        List<ReportDto> report = reportRepository.buildReport();

        try {
            String content = objectMapper.writeValueAsString(report);
            String path = generateReportFile(content);
            Report reportEntity = new Report();
            reportEntity.setReport(content);
            reportEntity.setPath(path);
            return reportRepository.save(reportEntity).getId();
        } catch (JsonProcessingException e) {
            logger.error("Cannot generate report", e);
            throw new IllegalStateException("Cannot generate report", e);
        }
    }
}
