package ru.skypro.lessons.springboot.springweb.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SourceType;

import java.time.Instant;

@Entity
@Data
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

    private  String path;

}
