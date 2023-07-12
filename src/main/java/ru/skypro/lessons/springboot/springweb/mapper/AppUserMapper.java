package ru.skypro.lessons.springboot.springweb.mapper;

import org.springframework.stereotype.Component;
import ru.skypro.lessons.springboot.springweb.entity.AppUser;
import ru.skypro.lessons.springboot.springweb.security.AppUserDetails;
import ru.skypro.lessons.springboot.springweb.security.AppUserDto;

@Component
public class AppUserMapper {

    public AppUserDto toDto(AppUser user) {
        AppUserDto userDto = new AppUserDto();
        userDto.setId(userDto.getId());
        userDto.setLogin(user.getLogin());
        userDto.setPassword(user.getPassword());
        userDto.setRole(user.getRole());
        return userDto;
    }
}
