package ru.skypro.lessons.springboot.springweb.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Profile("dev")
@RequestMapping("info")
public class InfoController {

    @Value("${dev}")
    private String dev;

    @GetMapping()
    public String appInfo() {
        return dev;
    }
}
