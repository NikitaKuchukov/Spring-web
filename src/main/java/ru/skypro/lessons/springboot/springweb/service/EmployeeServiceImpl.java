package ru.skypro.lessons.springboot.springweb.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.Nullable;
import lombok.RequiredArgsConstructor;
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

    private final EmployeeRepository employeeRepository;
    private final EmployeeMapper employeeMapper;
    private final ObjectMapper objectMapper;
    private final ReportRepository reportRepository;

    @Override
    public EmployeeDto findEmployeeWithMinSalary() {
        return employeeRepository.findEmployeeWithMinSalary()
                .map(employeeMapper::toDto)
                .orElse(null);
    }

    @Override
    public EmployeeDto findEmployeeWithMaxSalary() {
        return employeeRepository.findEmployeeWithMaxSalary()
                .map(employeeMapper::toDto)
                .orElse(null);
    }

    @Override
    public double findSumSalariesOfEmployees() {
        return employeeRepository.findSumSalariesOfEmployees();
    }

    @Override
    public List<EmployeeDto> findEmployeesWithAboveAverageSalaries() {
        return employeeRepository.findEmployeesWithAboveAverageSalaries();
    }

    @Override
    public List<EmployeeDto> getAll() {
        return employeeRepository.findAll().stream()
                .map(employeeMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<EmployeeDto> createEmployeeList(List<EmployeeDto> employees) {
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
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new EmployeeNotFoundException(id));
        employee.setName(employeeDto.getName());
        employee.setSalary(employeeDto.getSalary());
        employee.setPosition(employeeDto.getPosition());
        employeeRepository.save(employee);
    }

    @Override
    public EmployeeDto getEmployeeById(int id) {
        return employeeRepository.findById(id)
                .map(employeeMapper::toDto)
                .orElseThrow(() -> new EmployeeNotFoundException(id));

    }

    @Override
    public void deleteEmployeeById(int id) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new EmployeeNotFoundException(id));
        employeeRepository.delete(employee);
    }

    @Override
    public List<EmployeeDto> getEmployeesWithSalariesHigherThan(int salary) {
        return employeeRepository.findEmployeeBySalaryGreaterThan(salary).stream()
                .map(employeeMapper::toDto)
                .toList();
    }

    @Override
    public List<EmployeeDto> getEmployeesWithHighestSalaries() {
        return null;
    }

    @Override
    public List<EmployeeDto> getEmployees(@Nullable String position) {
        return Optional.ofNullable(position)
                .map(employeeRepository::findEmployeesByPosition_PositionContainingIgnoreCase)
                .orElseGet(employeeRepository::findAll)
                .stream()
                .map(employeeMapper::toDto)
                .toList();
    }

    @Override
    public List<EmployeeDto> getEmployeesFromPage(int page) {
        return employeeRepository.findAll(PageRequest.of(page, 10)).stream()
                .map(employeeMapper::toDto)
                .toList();
    }

    @Override
    public List<EmployeeDto> findEmployeesWithHighSalary() {
        return employeeRepository.findEmployeeWithHighSalary();

    }

    @Override
    public Resource findReport(int id) {
        return new ByteArrayResource(
                reportRepository.findById(id)
                        .orElseThrow(() -> new IllegalStateException("Report with id " + id + " not found"))
                        .getReport()
                        .getBytes(StandardCharsets.UTF_8));
    }

    @Override
    public void upload(MultipartFile file) {
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
        return reportRepository.findById(id)
                .map(Report::getPath)
                .map(File::new)
                .orElse(null);
    }

    public String generateReportFile(String content) {
        File f = new File("report_" + System.currentTimeMillis() + ".json");
        try (Writer writer = new FileWriter(f)) {
            writer.write(content);
        } catch (IOException e) {
            throw new UncheckedIOException("Cannot generate report file", e);
        }
        return f.getName();
    }

    @Override
    public int generateReport() {
        List<ReportDto> report = reportRepository.buildReport();

        try {
            String content = objectMapper.writeValueAsString(report);
            String path = generateReportFile(content);
            Report reportEntity = new Report();
            reportEntity.setReport(content);
            reportEntity.setPath(path);
            return reportRepository.save(reportEntity).getId();
        } catch (JsonProcessingException e) {
            throw new IllegalStateException("Cannot generate report", e);
        }
    }
}
