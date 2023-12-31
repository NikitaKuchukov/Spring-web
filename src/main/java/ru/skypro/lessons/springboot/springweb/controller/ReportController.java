package ru.skypro.lessons.springboot.springweb.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.lessons.springboot.springweb.service.EmployeeService;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

@RequiredArgsConstructor
@RestController
@RequestMapping("report")
public class ReportController {

    private final EmployeeService employeeService;

    @PostMapping()
    public int report() {
        return employeeService.generateReport();
    }

    @GetMapping("{id}")
    public ResponseEntity<Resource> findById(@PathVariable int id) {
        Resource resource = employeeService.findReport(id);
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"report.json\"")
                .body(resource);
    }

    @GetMapping("file/{id}")
    public ResponseEntity<Resource> findFile(@PathVariable int id) {
       File file = employeeService.findReportFile(id);
        if (file == null) {
            return ResponseEntity.noContent().build();
        }
        try {
            InputStreamResource resource = new InputStreamResource((new FileInputStream(file)));
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=report.json")
                    .contentLength(file.length())
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .body(resource);
        } catch (FileNotFoundException e) {
            return ResponseEntity.internalServerError().build();
        }
    }
}
