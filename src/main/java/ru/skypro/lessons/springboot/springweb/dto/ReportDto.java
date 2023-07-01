package ru.skypro.lessons.springboot.springweb.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ReportDto {

    private String position;
    private Long count;
    private int maxSalary;
    private int minSalary;
    private double averageSalary;

    public ReportDto(String position, Long count, int maxSalary, int minSalary, double averageSalary) {
        this.position = position;
        this.count = count;
        this.maxSalary = maxSalary;
        this.minSalary = minSalary;
        this.averageSalary = averageSalary;
    }
}
