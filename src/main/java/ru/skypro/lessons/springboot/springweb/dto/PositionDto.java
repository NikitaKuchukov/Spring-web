package ru.skypro.lessons.springboot.springweb.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PositionDto {

    private int id;
    private String name;

    public PositionDto(int id, String name) {
        this.id = id;
        this.name = name;
    }
}
