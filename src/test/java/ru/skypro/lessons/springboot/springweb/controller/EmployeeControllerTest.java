package ru.skypro.lessons.springboot.springweb.controller;

import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import ru.skypro.lessons.springboot.springweb.entity.Employee;
import ru.skypro.lessons.springboot.springweb.entity.Position;
import ru.skypro.lessons.springboot.springweb.repository.EmployeeRepository;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class EmployeeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private EmployeeRepository employeeRepository;


    @Test
    @SneakyThrows
    @WithMockUser
    void getAllTest() {

        Position position = new Position().setPosition("Dev");

        Employee employee = new Employee()
                .setName("Ant")
                .setPosition(position)
                .setSalary(123123);

        employeeRepository.save(employee);

        mockMvc.perform(get("/employee/all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(1));
    }
}
