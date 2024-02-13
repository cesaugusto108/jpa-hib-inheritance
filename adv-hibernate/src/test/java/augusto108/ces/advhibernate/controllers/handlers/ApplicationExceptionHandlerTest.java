package augusto108.ces.advhibernate.controllers.handlers;

import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
@DisplayNameGeneration(DisplayNameGenerator.Simple.class)
class ApplicationExceptionHandlerTest {

    private final String status = "NOT_FOUND";
    private final int statusCode = 404;

    private final MockMvc mockMvc;

    @Autowired
    ApplicationExceptionHandlerTest(MockMvc mockMvc) {
        this.mockMvc = mockMvc;
    }

    @Test
    void noHandlerFoundException() throws Exception {
        final String handlerNotFoundError = "org.springframework.web.servlet.NoHandlerFoundException: No endpoint GET /.";
        final String handlerNotFoundMessage = "No endpoint GET /.";

        mockMvc.perform(get("/"))
                .andExpect(status().is(404))
                .andExpect(content().contentType(MediaType.APPLICATION_PROBLEM_JSON))
                .andExpect(jsonPath("$.error", is(handlerNotFoundError)))
                .andExpect(jsonPath("$.message", is(handlerNotFoundMessage)))
                .andExpect(jsonPath("$.status", is(status)))
                .andExpect(jsonPath("$.statusCode", is(statusCode)));
    }

    @Test
    void noResultException() throws Exception {
        final String noResultExceptionError = "jakarta.persistence.NoResultException: No result found for id: 0";
        final String noResultExceptionMessage = "No result found for id: 0";

        mockMvc.perform(get("/employees/{id}", 0))
                .andExpect(status().is(404))
                .andExpect(content().contentType(MediaType.APPLICATION_PROBLEM_JSON))
                .andExpect(jsonPath("$.error", is(noResultExceptionError)))
                .andExpect(jsonPath("$.message", is(noResultExceptionMessage)))
                .andExpect(jsonPath("$.status", is(status)))
                .andExpect(jsonPath("$.statusCode", is(statusCode)));
    }
}