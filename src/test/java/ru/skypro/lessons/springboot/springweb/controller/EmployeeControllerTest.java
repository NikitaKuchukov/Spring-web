package ru.skypro.lessons.springboot.springweb.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import ru.skypro.lessons.springboot.springweb.repository.EmployeeRepository;
import ru.skypro.lessons.springboot.springweb.repository.PositionRepository;

import static org.hamcrest.Matchers.equalTo;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static ru.skypro.lessons.springboot.springweb.constants.Constants.*;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class EmployeeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private PositionRepository positionRepository;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    @SneakyThrows
    @WithMockUser
    void getAllTest() {

        mockMvc.perform(get("/employee/all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$").isEmpty());

        positionRepository.saveAll(POSITION_LIST);
        employeeRepository.saveAll(EMPLOYEE_LIST);

        mockMvc.perform(get("/employee/all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(8));
    }

    @Test
    @SneakyThrows
    @WithMockUser
    void getEmployeeWithMaxSalary() {

        positionRepository.saveAll(POSITION_LIST);
        employeeRepository.saveAll(EMPLOYEE_LIST);

        mockMvc.perform(get("/employee/salary/max"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(4))
                .andExpect(jsonPath("$.name").value("Irina"))
                .andExpect(jsonPath("$.salary").value(200_000))
                .andExpect(jsonPath("$.position").value(POSITION2));
    }

    @Test
    @SneakyThrows
    @WithMockUser
    void getEmployeeWithMinSalary() {

        positionRepository.saveAll(POSITION_LIST);
        employeeRepository.saveAll(EMPLOYEE_LIST);

        mockMvc.perform(get("/employee/salary/min"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Andrey"))
                .andExpect(jsonPath("$.salary").value(125_000))
                .andExpect(jsonPath("$.position").value(POSITION1));
    }

    @Test
    @SneakyThrows
    @WithMockUser
    void getSumSalariesOfEmployees() {

        positionRepository.saveAll(POSITION_LIST);
        employeeRepository.saveAll(EMPLOYEE_LIST);

        mockMvc.perform(get("/employee/salary/sum"))
                .andExpect(status().isOk())
                .andExpect(content().string(equalTo(String.valueOf(SUM_OF_SALARY))));
    }

    @Test
    @SneakyThrows
    @WithMockUser
    void findEmployeesWithAboveAverageSalaries() {

        positionRepository.saveAll(POSITION_LIST);
        employeeRepository.saveAll(EMPLOYEE_WITH_ABOVE_AVERAGE_SALARY);

        mockMvc.perform(get("/employee/salary/aboveAverage"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(EMPLOYEE_DTO_WITH_ABOVE_AVERAGE_SALARY.size()));
    }

    @Test
    @SneakyThrows
    @WithMockUser(roles = "ADMIN")
    void createEmployeeList() {

        positionRepository.saveAll(POSITION_LIST);

        mockMvc.perform(post("/employee/employeeList")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(EMPLOYEE_DTO_LIST)))
                .andExpect(status().isOk());
    }

    @Test
    @SneakyThrows
    @WithMockUser(roles = "ADMIN")
    void updateEmployeeById() {

        positionRepository.saveAll(POSITION_LIST);
        employeeRepository.save(EMPLOYEE);

        mockMvc.perform(put("/employee/" + 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(EMPLOYEE_DTO)))
                .andExpect(status().isOk());
    }

    @Test
    @SneakyThrows
    @WithMockUser
    void getEmployeeById() {

        positionRepository.saveAll(POSITION_LIST);
        employeeRepository.saveAll(EMPLOYEE_LIST);

        mockMvc.perform(get("/employee/" + 1))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Andrey"))
                .andExpect(jsonPath("$.salary").value(125_000))
                .andExpect(jsonPath("$.position").value(POSITION1));
    }

    @Test
    @SneakyThrows
    @WithMockUser(roles = "ADMIN")
    void deleteEmployeeById() {

        positionRepository.saveAll(POSITION_LIST);
        employeeRepository.saveAll(EMPLOYEE_LIST);

        mockMvc.perform(delete("/employee/" + 1))
                .andExpect(status().isOk());
    }

    @Test
    @SneakyThrows
    @WithMockUser
    void getEmployeesByPosition() {

        positionRepository.saveAll(POSITION_LIST);
        employeeRepository.saveAll(EMPLOYEE_LIST);

        mockMvc.perform(get("/employee")
                        .param("position", "d"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(5));
    }

    @Test
    @SneakyThrows
    @WithMockUser
    void getEmployeesFromPage() {

        positionRepository.saveAll(POSITION_LIST);
        employeeRepository.saveAll(EMPLOYEE_LIST);

        mockMvc.perform(get("/employee/page")
                        .param("page", "0"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(8));
    }

    @Test
    @SneakyThrows
    @WithMockUser
    void findEmployeesWithHighSalary() {

        positionRepository.saveAll(POSITION_LIST);
        employeeRepository.saveAll(EMPLOYEE_LIST);

        mockMvc.perform(get("/employee/withHighSalary"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(1));
    }


    @Test
    @SneakyThrows
    @WithMockUser(roles = "ADMIN")
    void upload() {
        positionRepository.saveAll(POSITION_LIST);

        MockMultipartFile file = new MockMultipartFile(
                "employees",
                objectMapper.writeValueAsString(EMPLOYEE_LIST).getBytes());

        mockMvc.perform(multipart("/employee/upload")
                        .file(file))
                .andExpect(status().isOk());

    }

}
