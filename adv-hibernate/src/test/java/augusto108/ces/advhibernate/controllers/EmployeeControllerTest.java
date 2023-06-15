package augusto108.ces.advhibernate.controllers;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
@DisplayNameGeneration(DisplayNameGenerator.Simple.class)
class EmployeeControllerTest {
    private final String jsonContent = "{\"id\":104,\"telephones\":[],\"name\":{\"firstName\":\"Ronaldo\",\"middleName\":\"Oliveira\"," +
            "\"lastName\":\"Santos\"},\"socialName\":{\"firstName\":\"Ronaldo\",\"middleName\":\"Oliveira\",\"lastName\":\"Santos\"}," +
            "\"email\":\"ronaldo@email.com\",\"role\":\"TRAINEE\"}";

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private MockMvc mockMvc;

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
        MvcResult result = mockMvc.perform(get("/employees/all?page=0&max=10"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON)).andReturn();

        assertTrue(result.getResponse().getContentAsString().contains(jsonContent));
    }

    @Test
    void getEmployeeById() throws Exception {
        mockMvc.perform(get("/employees/{id}", 104))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(jsonContent));
    }
}