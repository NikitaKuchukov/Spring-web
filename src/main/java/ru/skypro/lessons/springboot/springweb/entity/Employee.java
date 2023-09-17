package ru.skypro.lessons.springboot.springweb.entity;

import jakarta.persistence.*;
import lombok.*;

@Data
@Getter
@Entity
@Table(name = "employee")
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    private int salary;

    @ManyToOne
    @JoinColumn(name = "position_id")
    private Position position;
}
