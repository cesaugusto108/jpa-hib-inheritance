package augusto108.ces.advhibernate.services;

import augusto108.ces.advhibernate.domain.entities.Person;
import augusto108.ces.advhibernate.domain.entities.Student;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
class PersonServiceImplTest {
    @Autowired
    private PersonService personService;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @BeforeEach
    void setUp() {
        final String query = "insert into person " +
                "(`person_type`, `id`, `email`, `first_name`, `last_name`, `middle_name`, " +
                "`social_first_name`, `social_last_name`, `social_middle_name`, `employee_role`, `specialty`, `registration`) " +
                "values ('student', 1, 'daniela@email.com', 'Daniela', 'Cardoso', 'Santos', 'Daniela', " +
                "'Cardoso', 'Santos', NULL, NULL, '202000011200');";

        jdbcTemplate.execute(query);
    }

    @AfterEach
    void tearDown() {
        jdbcTemplate.execute("delete from person");
    }

    @Test
    void persistPerson() {
    }

    @Test
    void getStudents() {
        final List<Person> personList = personService.getStudents(0, 5);

        assertNotNull(personList);
        assertEquals(1, personList.size());
        assertEquals(1, personList.get(0).getId());
        assertEquals("Daniela", personList.get(0).getName().getFirstName());
        assertEquals("Santos", personList.get(0).getName().getMiddleName());
        assertEquals("Cardoso", personList.get(0).getName().getLastName());
        assertEquals("Daniela", personList.get(0).getSocialName().getFirstName());
        assertEquals("Santos", personList.get(0).getSocialName().getMiddleName());
        assertEquals("Cardoso", personList.get(0).getSocialName().getLastName());
    }

    @Test
    void getInstructors() {
    }

    @Test
    void getEmployees() {
    }

    @Test
    void getPersonById() {
    }
}