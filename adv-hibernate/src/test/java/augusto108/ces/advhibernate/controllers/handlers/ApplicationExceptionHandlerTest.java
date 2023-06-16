package augusto108.ces.advhibernate.controllers.handlers;

import augusto108.ces.advhibernate.controllers.StudentController;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
@DisplayNameGeneration(DisplayNameGenerator.IndicativeSentences.class)
class ApplicationExceptionHandlerTest {
    @Autowired
    private StudentController studentController;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void noHandlerFoundException() throws Exception {
        mockMvc.perform(get("/hibernate-studies/"))
                .andExpect(status().is(404))
                .andExpect(
                        jsonPath(
                                "$.error",
                                is("org.springframework.web.servlet.NoHandlerFoundException: No endpoint GET /hibernate-studies/."))
                )
                .andExpect(jsonPath("$.message", is("No endpoint GET /hibernate-studies/.")))
                .andExpect(jsonPath("$.status", is("NOT_FOUND")))
                .andExpect(jsonPath("$.statusCode", is(404)));
    }

    @Test
    void noResultException() throws Exception {
    }
}