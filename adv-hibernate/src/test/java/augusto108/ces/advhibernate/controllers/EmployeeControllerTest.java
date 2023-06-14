package augusto108.ces.advhibernate.controllers;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
@DisplayNameGeneration(DisplayNameGenerator.Simple.class)
class EmployeeControllerTest {
    @Autowired
    private EmployeeController employeeController;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private MockMvc mockMvc;

    private final String jsonContent = "{\"id\":104,\"name\":{\"firstName\":\"Ronaldo\",\"middleName\":\"Oliveira\",\"lastName\":\"Santos\"}," +
            "\"socialName\":{\"firstName\":\"Ronaldo\",\"middleName\":\"Oliveira\",\"lastName\":\"Santos\"},\"email\":\"ronaldo@email.com\"" +
            ",\"telephones\":[],\"role\":\"TRAINEE\"}";

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
        jdbcTemplate.execute("delete from person;");
    }

    @Test
    void getEmployees() throws Exception {
        mockMvc.perform(get("/employees/all"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json("[" + jsonContent + "]"));
    }

    @Test
    void getEmployeeById() {
    }
}