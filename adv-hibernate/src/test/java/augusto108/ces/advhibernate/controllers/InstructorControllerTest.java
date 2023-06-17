package augusto108.ces.advhibernate.controllers;

import augusto108.ces.advhibernate.domain.entities.Instructor;
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
class InstructorControllerTest {
    @Autowired
    private InstructorController instructorController;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Sql("/instructors-script.sql")
    @Test
    void getInstructors() throws Exception {
        MvcResult result = mockMvc.perform(
                        get("/instructors/all?page=0&max=10").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2)))
                .andReturn();

        final String jsonContent = result.getResponse().getContentAsString();

        final boolean containsId3002 = objectMapper
                .readValue(jsonContent, ArrayList.class).get(0).toString().contains("id=3002");
        final boolean containsId3003 = objectMapper
                .readValue(jsonContent, ArrayList.class).get(1).toString().contains("id=3003");

        assertTrue(containsId3002 && containsId3003);
    }

    @Sql("/instructors-script.sql")
    @Test
    void getInstructorById() throws Exception {
        MvcResult result = mockMvc.perform(
                        get("/instructors/{id}", 3002).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(3002)))
                .andExpect(jsonPath("$.specialty", is("Kotlin")))
                .andReturn();

        final String jsonContent = result.getResponse().getContentAsString();
        final Instructor instructor = objectMapper.readValue(jsonContent, Instructor.class);

        assertEquals("Luciana Silva Santos (Kotlin)", instructor.toString());
    }
}