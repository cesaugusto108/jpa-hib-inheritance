package augusto108.ces.advhibernate.controllers;

import augusto108.ces.advhibernate.domain.entities.Instructor;
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
class InstructorControllerTest {

    private final String jsonContent1 = "{\"id\":102,\"name\":{\"firstName\":\"Milena\",\"middleName\":\"Freitas\",\"lastName\":\"Andrade\"}," +
            "\"socialName\":{\"firstName\":\"Milena\",\"middleName\":\"Freitas\",\"lastName\":\"Andrade\"},\"email\":\"milena@email.com\",\"telephones\":[]," +
            "\"specialty\":\"Java\"}";

    private final JdbcTemplate jdbcTemplate;
    private final MockMvc mockMvc;
    private final ObjectMapper objectMapper;

    @Autowired
    InstructorControllerTest(JdbcTemplate jdbcTemplate, MockMvc mockMvc, ObjectMapper objectMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.mockMvc = mockMvc;
        this.objectMapper = objectMapper;
    }

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
        jdbcTemplate.execute("delete from person_telephone;");
        jdbcTemplate.execute("delete from telephone;");
        jdbcTemplate.execute("delete from person;");
    }

    @Test
    void getInstructors() throws Exception {
        mockMvc.perform(get("/instructors/all?page=0&max=10"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(content().json("[" + jsonContent1 + "]"));
    }

    @Test
    void getInstructorById() throws Exception {
        MvcResult result = mockMvc.perform(get("/instructors/{id}", 102))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(jsonContent1))
                .andReturn();

        final String json = result.getResponse().getContentAsString();
        final Instructor instructor = objectMapper.readValue(json, Instructor.class);
        assertEquals("Milena Freitas Andrade (Java)", instructor.toString());
    }
}