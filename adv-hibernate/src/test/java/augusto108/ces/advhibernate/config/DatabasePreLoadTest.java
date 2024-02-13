package augusto108.ces.advhibernate.config;

import augusto108.ces.advhibernate.data.EmployeeLoad;
import augusto108.ces.advhibernate.data.InstructorLoad;
import augusto108.ces.advhibernate.data.StudentLoad;
import augusto108.ces.advhibernate.domain.entities.Employee;
import augusto108.ces.advhibernate.domain.entities.Instructor;
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

    private final PersonService personService;
    private final TelephoneService telephoneService;
    private final StudentLoad studentLoad;
    private final InstructorLoad instructorLoad;
    private final EmployeeLoad employeeLoad;
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    DatabasePreLoadTest(PersonService personService, TelephoneService telephoneService, StudentLoad studentLoad, InstructorLoad instructorLoad, EmployeeLoad employeeLoad, JdbcTemplate jdbcTemplate) {
        this.personService = personService;
        this.telephoneService = telephoneService;
        this.studentLoad = studentLoad;
        this.instructorLoad = instructorLoad;
        this.employeeLoad = employeeLoad;
        this.jdbcTemplate = jdbcTemplate;
    }

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
    @DisplayName("Persisting an instructor")
    void persistInstructor() {
        final Instructor instructor = instructorLoad.createInstructor();
        final Telephone[] telephones = instructorLoad.createTelephones();

        assertNotNull(instructor);
        assertNotNull(telephones);

        for (Telephone telephone : telephones) {
            telephoneService.persistTelephone(telephone);
        }

        personService.persistPerson(instructor);

        final List<Telephone> telephoneList = telephoneService.getTelephones(0, 5);
        final List<Instructor> instructorList = personService.getInstructors(0, 5);

        assertNotNull(telephoneList);
        assertNotNull(instructorList);

        assertEquals(2, telephoneList.size());
        assertEquals(1, instructorList.size());
        assertEquals("55", telephoneList.get(0).getCountryCode());
        assertEquals("55", telephoneList.get(1).getCountryCode());
        assertEquals("79", telephoneList.get(0).getAreaCode());
        assertEquals("79", telephoneList.get(1).getAreaCode());
        assertEquals("999999999", telephoneList.get(0).getNumber());
        assertEquals("999991111", telephoneList.get(1).getNumber());
        assertEquals("Antonio", instructorList.get(0).getName().getFirstName());
        assertEquals("Jorge", instructorList.get(0).getName().getMiddleName());
        assertEquals("Sá", instructorList.get(0).getName().getLastName());
        assertEquals("Antonio", instructorList.get(0).getSocialName().getFirstName());
        assertEquals("Jorge", instructorList.get(0).getSocialName().getMiddleName());
        assertEquals("Sá", instructorList.get(0).getSocialName().getLastName());
        assertEquals("antonio@email.com", instructorList.get(0).getEmail());
        assertEquals("Redes", instructorList.get(0).getSpecialty());
    }

    @Test
    @DisplayName("Persisting an employee")
    void persistEmployee() {
        final Employee employee = employeeLoad.createEmployee();
        final Telephone[] telephones = employeeLoad.createTelephones();

        assertNotNull(employee);
        assertNotNull(telephones);

        for (Telephone telephone : telephones) {
            telephoneService.persistTelephone(telephone);
        }

        personService.persistPerson(employee);

        final List<Telephone> telephoneList = telephoneService.getTelephones(0, 5);
        final List<Employee> employeeList = personService.getEmployees(0, 5);

        assertNotNull(telephoneList);
        assertNotNull(employeeList);

        assertEquals(2, telephoneList.size());
        assertEquals(1, employeeList.size());
        assertEquals("55", telephoneList.get(0).getCountryCode());
        assertEquals("55", telephoneList.get(1).getCountryCode());
        assertEquals("79", telephoneList.get(0).getAreaCode());
        assertEquals("79", telephoneList.get(1).getAreaCode());
        assertEquals("999993333", telephoneList.get(0).getNumber());
        assertEquals("999994444", telephoneList.get(1).getNumber());
        assertEquals("Marieta", employeeList.get(0).getName().getFirstName());
        assertEquals("Santos", employeeList.get(0).getName().getMiddleName());
        assertEquals("Araújo", employeeList.get(0).getName().getLastName());
        assertEquals("Marieta", employeeList.get(0).getSocialName().getFirstName());
        assertEquals("Santos", employeeList.get(0).getSocialName().getMiddleName());
        assertEquals("Araújo", employeeList.get(0).getSocialName().getLastName());
        assertEquals("marieta@email.com", employeeList.get(0).getEmail());
        assertEquals("ADMIN", employeeList.get(0).getRole().toString());
    }
}