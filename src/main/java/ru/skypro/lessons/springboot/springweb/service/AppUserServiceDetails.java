package ru.skypro.lessons.springboot.springweb.service;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import ru.skypro.lessons.springboot.springweb.entity.AppUser;
import ru.skypro.lessons.springboot.springweb.mapper.AppUserMapper;
import ru.skypro.lessons.springboot.springweb.repository.AppUserRepository;
import ru.skypro.lessons.springboot.springweb.security.AppUserDetails;
@RequiredArgsConstructor
@Component
public class AppUserServiceDetails implements UserDetailsService {
    private final AppUserRepository repository;
    private final AppUserMapper mapper;
    private final AppUserDetails appUserDetails;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AppUser appUser = repository.findAppUserByLogin(username)
                .orElseThrow(() -> new IllegalStateException("User not found"));
        appUserDetails.setUserDetails(mapper.toDto(appUser));
        return appUserDetails;
    }
}
