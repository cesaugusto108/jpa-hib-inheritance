package augusto108.ces.advhibernate.config;

import augusto108.ces.advhibernate.data.EmployeeLoad;
import augusto108.ces.advhibernate.data.InstructorLoad;
import augusto108.ces.advhibernate.data.StudentLoad;
import augusto108.ces.advhibernate.domain.entities.Employee;
import augusto108.ces.advhibernate.domain.entities.Instructor;
import augusto108.ces.advhibernate.domain.entities.Student;
import augusto108.ces.advhibernate.services.PersonService;
import augusto108.ces.advhibernate.services.TelephoneService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@ActiveProfiles("databasetest")
@DisplayNameGeneration(DisplayNameGenerator.IndicativeSentences.class)
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

    private DatabasePreLoad databasePreLoad;

    @PersistenceContext
    private EntityManager entityManager;

    @BeforeEach
    void setUp() {
        databasePreLoad = new DatabasePreLoad(personService, telephoneService, studentLoad, instructorLoad, employeeLoad);
    }

    @AfterEach
    void tearDown() {
        entityManager.createNativeQuery("delete from student;");
        entityManager.createNativeQuery("delete from instructor;");
        entityManager.createNativeQuery("delete from employee;");
    }

    @Test
    void persistStudent() {
        databasePreLoad.persistStudent();

        final List<Student> students = entityManager
                .createQuery("from Student order by id", Student.class)
                .getResultList();

        assertEquals(1, students.size());
        assertEquals("Carlos Cardoso Farias (202000089000)", students.get(0).toString());
        assertEquals("+55 (71) 999990000", students.get(0).getTelephones().toArray()[0].toString());
    }

    @Test
    void persistInstructor() {
        databasePreLoad.persistInstructor();

        final List<Instructor> instructors = entityManager
                .createQuery("from Instructor order by id", Instructor.class)
                .getResultList();

        assertEquals(1, instructors.size());
        assertEquals("Antonio Jorge Sá (Redes)", instructors.get(0).toString());
        assertEquals("+55 (79) 999999999", instructors.get(0).getTelephones().toArray()[0].toString());
        assertEquals("+55 (79) 999991111", instructors.get(0).getTelephones().toArray()[1].toString());
    }

    @Test
    void persistEmployee() {
        databasePreLoad.persistEmployee();

        final List<Employee> employees = entityManager
                .createQuery("from Employee order by id", Employee.class)
                .getResultList();

        assertEquals(1, employees.size());
        assertEquals("Marieta Santos Araújo (ADMIN)", employees.get(0).toString());
        assertEquals("+55 (79) 999993333", employees.get(0).getTelephones().toArray()[0].toString());
        assertEquals("+55 (79) 999994444", employees.get(0).getTelephones().toArray()[1].toString());
    }
}