package ru.skypro.lessons.springboot.springweb.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
public class PositionDto {

    private int id;
    private String position;

    public PositionDto(int id, String position) {
        this.id = id;
        this.position = position;
    }
}
