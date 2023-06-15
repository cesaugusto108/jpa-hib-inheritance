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
    private final String jsonContent1 = "{\"id\":102,\"name\":{\"firstName\":\"Milena\",\"middleName\":\"Freitas\",\"lastName\":\"Andrade\"}," +
            "\"socialName\":{\"firstName\":\"Milena\",\"middleName\":\"Freitas\",\"lastName\":\"Andrade\"},\"email\":\"milena@email.com\",\"telephones\":[]," +
            "\"specialty\":\"Java\"}";

    private final String jsonContent2 = "{\"id\":2,\"telephones\":[{\"id\":2,\"countryCode\":\"55\",\"areaCode\":\"79\",\"number\":\"999999999\"}," +
            "{\"id\":3,\"countryCode\":\"55\",\"areaCode\":\"79\",\"number\":\"999991111\"}],\"name\":{\"firstName\":\"Antonio\",\"middleName\":\"Jorge\",\"lastName\":\"Sá\"}," +
            "\"socialName\":{\"firstName\":\"Antonio\",\"middleName\":\"Jorge\",\"lastName\":\"Sá\"},\"email\":\"antonio@email.com\",\"specialty\":\"Redes\"}";

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
        jdbcTemplate.execute("delete from person_telephone;");
        jdbcTemplate.execute("delete from telephone;");
        jdbcTemplate.execute("delete from person;");
    }

    @Test
    void getInstructors() throws Exception {
        mockMvc.perform(get("/instructors/all"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json("[" + jsonContent2 + "," + jsonContent1 + "]"));
    }

    @Test
    void getInstructorById() throws Exception {
        mockMvc.perform(get("/instructors/{id}", 102))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(jsonContent1));
    }
}