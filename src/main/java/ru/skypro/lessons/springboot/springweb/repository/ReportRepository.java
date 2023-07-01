package ru.skypro.lessons.springboot.springweb.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.skypro.lessons.springboot.springweb.dto.ReportDto;
import ru.skypro.lessons.springboot.springweb.entity.Report;

import java.util.List;

@Repository
public interface ReportRepository extends JpaRepository<Report, Integer> {
    @Query("""
           SELECT new ru.skypro.lessons.springboot.springweb.dto.ReportDto(e.position.position, count(e.id), max(e.salary), min(e.salary), avg(e.salary))
           FROM Employee e
           GROUP BY e.position.position
           """)
    List<ReportDto> buildReport();
}
