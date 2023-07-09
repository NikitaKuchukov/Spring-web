package ru.skypro.lessons.springboot.springweb.security;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.skypro.lessons.springboot.springweb.entity.Role;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AppUserDto {

    private int id;
    private String login;
    private String password;
    private Role role;

}
