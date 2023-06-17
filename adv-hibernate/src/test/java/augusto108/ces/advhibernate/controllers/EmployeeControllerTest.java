package augusto108.ces.advhibernate.controllers;

import augusto108.ces.advhibernate.domain.entities.Employee;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@Transactional
@ActiveProfiles("test")
@AutoConfigureMockMvc
@DisplayNameGeneration(DisplayNameGenerator.Simple.class)
class EmployeeControllerTest {
    @Autowired
    private EmployeeController employeeController;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Sql("/employees-script.sql")
    @Test
    void getEmployees() throws Exception {
        final MvcResult result = mockMvc.perform(
                        get("/employees/all?page=0&max=10").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2)))
                .andReturn();

        final String jsonContent = result.getResponse().getContentAsString();

        final boolean containsId3004 = objectMapper
                .readValue(jsonContent, ArrayList.class).get(0).toString().contains("id=3004");
        final boolean containsId3005 = objectMapper
                .readValue(jsonContent, ArrayList.class).get(1).toString().contains("id=3005");

        assertTrue(containsId3004 && containsId3005);
    }

    @Sql("/employees-script.sql")
    @Test
    void getEmployeeById() throws Exception {
        final MvcResult result = mockMvc.perform(
                        get("/employees/{id}", 3005).contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(3005)))
                .andExpect(jsonPath("$.telephones", hasSize(0)))
                .andExpect(jsonPath("$.role", is("TRAINEE")))
                .andReturn();

        final String jsonContent = result.getResponse().getContentAsString();
        final Employee employee = objectMapper.readValue(jsonContent, Employee.class);

        assertEquals(employee.toString(), "Fernanda Carvalho Martins (TRAINEE)");
    }
}