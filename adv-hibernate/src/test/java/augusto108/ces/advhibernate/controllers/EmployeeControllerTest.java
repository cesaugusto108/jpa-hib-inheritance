package augusto108.ces.advhibernate.controllers;

import augusto108.ces.advhibernate.domain.entities.Employee;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
@DisplayNameGeneration(DisplayNameGenerator.Simple.class)
class EmployeeControllerTest {

    private final String jsonContent = "{\"id\":104,\"telephones\":[],\"name\":{\"firstName\":\"Ronaldo\",\"middleName\":\"Oliveira\"," +
            "\"lastName\":\"Santos\"},\"socialName\":{\"firstName\":\"Ronaldo\",\"middleName\":\"Oliveira\",\"lastName\":\"Santos\"}," +
            "\"email\":\"ronaldo@email.com\",\"role\":\"TRAINEE\"}";

    private final JdbcTemplate jdbcTemplate;
    private final MockMvc mockMvc;
    private final ObjectMapper objectMapper;

    @Autowired
    EmployeeControllerTest(JdbcTemplate jdbcTemplate, MockMvc mockMvc, ObjectMapper objectMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.mockMvc = mockMvc;
        this.objectMapper = objectMapper;
    }

    @BeforeEach
    void setUp() {
        final String employeeQuery = "insert " +
                "into person (`person_type`, `id`, `email`, `first_name`, `last_name`, `middle_name`, `social_first_name`, " +
                "`social_last_name`, `social_middle_name`, `employee_role`, `specialty`, `registration`)\n" +
                "values ('employee', 104, 'ronaldo@email.com', 'Ronaldo', 'Santos', 'Oliveira', 'Ronaldo', 'Santos', " +
                "'Oliveira', 'TRAINEE', NULL, NULL);";

        jdbcTemplate.execute(employeeQuery);
    }

    @AfterEach
    void tearDown() {
        jdbcTemplate.execute("delete from person_telephone;");
        jdbcTemplate.execute("delete from telephone;");
        jdbcTemplate.execute("delete from person;");
    }

    @Test
    void getEmployees() throws Exception {
        mockMvc.perform(get("/employees/all?page=0&max=10"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(content().json("[" + jsonContent + "]"));
    }

    @Test
    void getEmployeeById() throws Exception {
        MvcResult result = mockMvc.perform(get("/employees/{id}", 104))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(jsonContent))
                .andReturn();

        final String json = result.getResponse().getContentAsString();
        final Employee employee = objectMapper.readValue(json, Employee.class);
        assertEquals("Ronaldo Oliveira Santos (TRAINEE)", employee.toString());
    }
}