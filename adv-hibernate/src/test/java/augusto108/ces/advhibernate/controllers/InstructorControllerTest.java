package augusto108.ces.advhibernate.controllers;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
@DisplayNameGeneration(DisplayNameGenerator.Simple.class)
class InstructorControllerTest {
    private final String jsonContent = "{\"id\":102,\"name\":{\"firstName\":\"Milena\",\"middleName\":\"Freitas\",\"lastName\":\"Andrade\"}," +
            "\"socialName\":{\"firstName\":\"Milena\",\"middleName\":\"Freitas\",\"lastName\":\"Andrade\"},\"email\":\"milena@email.com\",\"telephones\":[]," +
            "\"specialty\":\"Java\"}";
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        final String instructorQuery = "insert " +
                "into person (`person_type`, `id`, `email`, `first_name`, `last_name`, `middle_name`, `social_first_name`, `social_last_name`, " +
                "`social_middle_name`, `employee_role`, `specialty`, `registration`) " +
                "values ('instructor', 102, 'milena@email.com', 'Milena', 'Andrade', 'Freitas', 'Milena', 'Andrade', " +
                "'Freitas', NULL, 'Java', NULL);";

        jdbcTemplate.execute(instructorQuery);
    }

    @AfterEach
    void tearDown() {
        jdbcTemplate.execute("delete from person;");
    }

    @Test
    void getInstructors() throws Exception {
        mockMvc.perform(get("/instructors/all"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json("[" + jsonContent + "]"));
    }

    @Test
    void getInstructorById() throws Exception {
        mockMvc.perform(get("/instructors/{id}", 102))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(jsonContent));
    }
}