package augusto108.ces.advhibernate.config;

import augusto108.ces.advhibernate.data.EmployeeLoad;
import augusto108.ces.advhibernate.data.InstructorLoad;
import augusto108.ces.advhibernate.data.StudentLoad;
import augusto108.ces.advhibernate.domain.entities.Student;
import augusto108.ces.advhibernate.domain.entities.Telephone;
import augusto108.ces.advhibernate.services.PersonService;
import augusto108.ces.advhibernate.services.TelephoneService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@ActiveProfiles("databasetest")
class DatabasePreLoadTest {
    @Autowired
    private PersonService personService;

    @Autowired
    private TelephoneService telephoneService;

    @Autowired
    private StudentLoad studentLoad;

    @Autowired
    private InstructorLoad instructorLoad;

    @Autowired
    private EmployeeLoad employeeLoad;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
        jdbcTemplate.execute("delete from person_telephone;");
        jdbcTemplate.execute("delete from telephone;");
        jdbcTemplate.execute("delete from person;");
    }

    @Test
    @DisplayName("Persisting a student")
    void persistStudent() {
        final Student student = studentLoad.createStudent();
        final Telephone telephone = studentLoad.createStudentTelephone();

        assertNotNull(student);
        assertNotNull(telephone);

        telephoneService.persistTelephone(telephone);

        student.getTelephones().add(telephone);

        personService.persistPerson(student);

        final List<Telephone> telephoneList = telephoneService.getTelephones(0, 5);
        final List<Student> studentList = personService.getStudents(0, 5);

        assertNotNull(studentList);
        assertNotNull(telephoneList);

        assertEquals(1, telephoneList.get(0).getId());
        assertEquals(1, studentList.get(0).getId());
        assertEquals("55", telephoneList.get(0).getCountryCode());
        assertEquals("71", telephoneList.get(0).getAreaCode());
        assertEquals("999990000", telephoneList.get(0).getNumber());
        assertEquals("Carlos", studentList.get(0).getName().getFirstName());
        assertEquals("Cardoso", studentList.get(0).getName().getMiddleName());
        assertEquals("Farias", studentList.get(0).getName().getLastName());
        assertEquals("Carlos", studentList.get(0).getSocialName().getFirstName());
        assertEquals("Cardoso", studentList.get(0).getSocialName().getMiddleName());
        assertEquals("Farias", studentList.get(0).getSocialName().getLastName());
    }

    @Test
    void persistInstructor() {
    }

    @Test
    void persistEmployee() {
    }
}