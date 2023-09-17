package ru.skypro.lessons.springboot.springweb.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SourceType;

import java.time.Instant;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Report {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Lob
    @Column(columnDefinition = "oid", nullable = false)
    private String report;

    @CreationTimestamp(source = SourceType.DB)
    @Column(updatable = false, name = "created_at", nullable = false)
    private Instant createdAd;

    private String path;

}
