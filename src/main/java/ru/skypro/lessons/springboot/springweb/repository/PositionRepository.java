package ru.skypro.lessons.springboot.springweb.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.skypro.lessons.springboot.springweb.entity.Position;

public interface PositionRepository extends JpaRepository<Position, Integer> {
}
