package augusto108.ces.advhibernate.controllers;

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

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@Transactional
@ActiveProfiles("test")
@AutoConfigureMockMvc
@DisplayNameGeneration(DisplayNameGenerator.Simple.class)
class StudentControllerTest {
    @Autowired
    private StudentController studentController;

    @Autowired
    private MockMvc mockMvc;

    private final String jsonContent = "{\"id\":3000,\"telephones\":[],\"name\":{\"firstName\":\"Larissa\",\"middleName\":\"Pereira\"," +
            "\"lastName\":\"Castro\"},\"socialName\":{\"firstName\":\"Larissa\",\"middleName\":\"Pereira\",\"lastName\":\"Castro\"}," +
            "\"email\":\"larissa@email.com\",\"registration\":\"202000011201\"}";

    @Sql("/students-script.sql")
    @Test
    void getStudents() throws Exception {
        final MvcResult result = mockMvc.perform(get("/students/all").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2))).andReturn();

        assertTrue(result.getResponse().getContentAsString().contains(jsonContent));
    }

    @Sql("/students-script.sql")
    @Test
    void getStudentById() throws Exception {
        final MvcResult result = mockMvc.perform(get("/students/{id}", 3000).contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(3000)))
                .andExpect(jsonPath("$.telephones", hasSize(0)))
                .andExpect(jsonPath("$.registration", is("202000011201"))).andReturn();

        assertTrue(result.getResponse().getContentAsString().contains(jsonContent));
    }
}