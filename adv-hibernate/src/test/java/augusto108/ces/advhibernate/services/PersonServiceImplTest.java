package augusto108.ces.advhibernate.services;

import augusto108.ces.advhibernate.domain.entities.*;
import augusto108.ces.advhibernate.domain.entities.enums.Role;
import jakarta.persistence.NoResultException;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@DisplayNameGeneration(DisplayNameGenerator.Simple.class)
class PersonServiceImplTest {
    @Autowired
    private PersonService personService;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    void persistPerson() {
        final Name studentName = new Name("Pedro", "Santos", "Barros");
        final Name studentSocialName = new Name("Pedro", "Santos", "Barros");
        final Student student = new Student(studentName, studentSocialName, "pedro@email.com", "202200020100");

        final Name instructorName = new Name("Patrícia", "Alves", "Silva");
        final Name instructorSocialName = new Name("Patrícia", "Alves", "Silva");
        final Instructor instructor = new Instructor(instructorName, instructorSocialName, "patricia@email.com", "Spring");

        final Name employeeName = new Name("Luís", "Batista", "Mota");
        final Name employeeSocialName = new Name("Pedro", "Batista", "Mota");
        final Employee employee = new Employee(employeeName, employeeSocialName, "luis@email.com", Role.MANAGER);

        personService.persistPerson(student);
        personService.persistPerson(instructor);
        personService.persistPerson(employee);

        assertNotNull(personService.getPersonById(1));
        assertNotNull(personService.getPersonById(2));
        assertNotNull(personService.getPersonById(3));

        assertEquals(1, personService.getStudents(0, 5).size());
        assertEquals(1, personService.getInstructors(0, 5).size());
        assertEquals(1, personService.getEmployees(0, 5).size());
        assertEquals("202200020100", personService.getStudents(0, 5).get(0).getRegistration());
        assertEquals("Spring", personService.getInstructors(0, 5).get(0).getSpecialty());
        assertEquals("MANAGER", personService.getEmployees(0, 5).get(0).getRole().toString());

        jdbcTemplate.execute("delete from person;");
    }

    @Nested
    class NestedTests {
        @BeforeEach
        void setUp() {
            final String studentQuery = "insert into person " +
                    "(`person_type`, `id`, `email`, `first_name`, `last_name`, `middle_name`, " +
                    "`social_first_name`, `social_last_name`, `social_middle_name`, `employee_role`, `specialty`, `registration`) " +
                    "values ('student', 100, 'daniela@email.com', 'Daniela', 'Cardoso', 'Santos', 'Daniela', " +
                    "'Cardoso', 'Santos', NULL, NULL, '202000011200');";

            final String instructorQuery = "insert into person " +
                    "(`person_type`, `id`, `email`, `first_name`, `last_name`, `middle_name`, " +
                    "`social_first_name`, `social_last_name`, `social_middle_name`, `employee_role`, `specialty`, `registration`) " +
                    "values ('instructor', 101, 'paulo@email.com', 'Paulo', 'Oliveira', 'Silva', 'Paulo', " +
                    "'Oliveira', 'Silva', NULL, 'Java', NULL);";

            final String employeeQuery = "insert into person " +
                    "(`person_type`, `id`, `email`, `first_name`, `last_name`, `middle_name`, " +
                    "`social_first_name`, `social_last_name`, `social_middle_name`, `employee_role`, `specialty`, `registration`) " +
                    "values ('employee', 102, 'renata@email.com', 'Renata', 'Costa', 'Martins', 'Renata', " +
                    "'Costa', 'Martins', 'ADMIN', NULL, NULL);";

            jdbcTemplate.execute(studentQuery);
            jdbcTemplate.execute(instructorQuery);
            jdbcTemplate.execute(employeeQuery);
        }

        @AfterEach
        void tearDown() {
            jdbcTemplate.execute("delete from person");
        }

        @Test
        void getStudents() {
            final List<Student> studentList = personService.getStudents(0, 5);

            assertNotNull(studentList);
            assertEquals(1, studentList.size());
            assertEquals(Student.class, studentList.get(0).getClass());
            assertEquals(100, studentList.get(0).getId());
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
            assertEquals(101, instructorList.get(0).getId());
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
            final List<Employee> employeeList = personService.getEmployees(0, 5);

            assertNotNull(employeeList);
            assertEquals(1, employeeList.size());
            assertEquals(Employee.class, employeeList.get(0).getClass());
            assertEquals(102, employeeList.get(0).getId());
            assertEquals("Renata", employeeList.get(0).getName().getFirstName());
            assertEquals("Martins", employeeList.get(0).getName().getMiddleName());
            assertEquals("Costa", employeeList.get(0).getName().getLastName());
            assertEquals("Renata", employeeList.get(0).getSocialName().getFirstName());
            assertEquals("Martins", employeeList.get(0).getSocialName().getMiddleName());
            assertEquals("Costa", employeeList.get(0).getSocialName().getLastName());
            assertEquals("ADMIN", employeeList.get(0).getRole().toString());
        }

        @Test
        void getPersonById() {
            final Person student = personService.getPersonById(100);
            final Person instructor = personService.getPersonById(101);
            final Person employee = personService.getPersonById(102);

            assertNotNull(student);
            assertNotNull(instructor);
            assertNotNull(employee);

            assertEquals(100, student.getId());
            assertEquals(101, instructor.getId());
            assertEquals(102, employee.getId());

            assertThrows(NoResultException.class, () -> personService.getPersonById(0));
        }
    }
}