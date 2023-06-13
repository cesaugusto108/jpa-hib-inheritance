package augusto108.ces.advhibernate.services;

import augusto108.ces.advhibernate.domain.entities.Instructor;
import augusto108.ces.advhibernate.domain.entities.Student;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@ActiveProfiles("test")
class PersonServiceImplTest {
    @Autowired
    private PersonService personService;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @BeforeEach
    void setUp() {
        final String studentQuery = "insert into person " +
                "(`person_type`, `id`, `email`, `first_name`, `last_name`, `middle_name`, " +
                "`social_first_name`, `social_last_name`, `social_middle_name`, `employee_role`, `specialty`, `registration`) " +
                "values ('student', 1, 'daniela@email.com', 'Daniela', 'Cardoso', 'Santos', 'Daniela', " +
                "'Cardoso', 'Santos', NULL, NULL, '202000011200');";

        final String instructorQuery = "insert into person " +
                "(`person_type`, `id`, `email`, `first_name`, `last_name`, `middle_name`, " +
                "`social_first_name`, `social_last_name`, `social_middle_name`, `employee_role`, `specialty`, `registration`) " +
                "values ('instructor', 2, 'paulo@email.com', 'Paulo', 'Oliveira', 'Silva', 'Paulo', " +
                "'Oliveira', 'Silva', NULL, 'Java', NULL);";

        jdbcTemplate.execute(studentQuery);
        jdbcTemplate.execute(instructorQuery);
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
        final List<Student> studentList = personService.getStudents(0, 5);

        assertNotNull(studentList);
        assertEquals(1, studentList.size());
        assertEquals(Student.class, studentList.get(0).getClass());
        assertEquals(1, studentList.get(0).getId());
        assertEquals("Daniela", studentList.get(0).getName().getFirstName());
        assertEquals("Santos", studentList.get(0).getName().getMiddleName());
        assertEquals("Cardoso", studentList.get(0).getName().getLastName());
        assertEquals("Daniela", studentList.get(0).getSocialName().getFirstName());
        assertEquals("Santos", studentList.get(0).getSocialName().getMiddleName());
        assertEquals("Cardoso", studentList.get(0).getSocialName().getLastName());
        assertEquals("202000011200", studentList.get(0).getRegistration());
    }

    @Test
    void getInstructors() {
        final List<Instructor> instructorList = personService.getInstructors(0, 5);

        assertNotNull(instructorList);
        assertEquals(1, instructorList.size());
        assertEquals(Instructor.class, instructorList.get(0).getClass());
        assertEquals(2, instructorList.get(0).getId());
        assertEquals("Paulo", instructorList.get(0).getName().getFirstName());
        assertEquals("Silva", instructorList.get(0).getName().getMiddleName());
        assertEquals("Oliveira", instructorList.get(0).getName().getLastName());
        assertEquals("Paulo", instructorList.get(0).getSocialName().getFirstName());
        assertEquals("Silva", instructorList.get(0).getSocialName().getMiddleName());
        assertEquals("Oliveira", instructorList.get(0).getSocialName().getLastName());
        assertEquals("Java", instructorList.get(0).getSpecialty());
    }

    @Test
    void getEmployees() {
    }

    @Test
    void getPersonById() {
    }
}