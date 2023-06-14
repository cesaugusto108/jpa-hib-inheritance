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
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
@DisplayNameGeneration(DisplayNameGenerator.Simple.class)
class StudentControllerTest {
    @Autowired
    private StudentController studentController;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private MockMvc mockMvc;

    private final String jsonContent = "{\"id\":100,\"name\":{\"firstName\":\"Larissa\",\"middleName\":\"Pereira\",\"lastName\":\"Castro\"}," +
            "\"socialName\":{\"firstName\":\"Larissa\",\"middleName\":\"Pereira\",\"lastName\":\"Castro\"},\"email\":\"larissa@email.com\"," +
            "\"telephones\":[],\"registration\":\"202000011201\"}";

    @BeforeEach
    void setUp() {
        final String studentQuery = "insert " +
                "into person (`person_type`, `id`, `email`, `first_name`, `last_name`, `middle_name`, `social_first_name`, " +
                "`social_last_name`, `social_middle_name`, `employee_role`, `specialty`, `registration`)" +
                " values ('student', 100, 'larissa@email.com', 'Larissa', 'Castro', 'Pereira', 'Larissa', 'Castro', " +
                "'Pereira', NULL, NULL, '202000011201');";

        jdbcTemplate.execute(studentQuery);
    }

    @AfterEach
    void tearDown() {
        jdbcTemplate.execute("delete from person");
    }

    @Test
    void getStudents() throws Exception {
        mockMvc.perform(get("/students/all"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json("[" + jsonContent + "]"));
    }

    @Test
    void getStudentById() throws Exception {
        mockMvc.perform(get("/students/{id}", 100))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(jsonContent));
    }
}