package augusto108.ces.advhibernate.services;

import augusto108.ces.advhibernate.domain.entities.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@ActiveProfiles("test")
@DisplayNameGeneration(DisplayNameGenerator.Simple.class)
class PersonServiceImplTest {
    @Autowired
    private PersonService personService;

    @PersistenceContext
    private EntityManager entityManager;

    @BeforeEach
    void setUp() {
        entityManager
                .createNativeQuery("insert into person (`id`, `email`, `first_name`, `last_name`, `middle_name`, " +
                        "`social_first_name`, `social_last_name`, `social_middle_name`) " +
                        "values (20000, 'jorge@email.com', 'Jorge', 'Brito', 'Santana', 'Jorge', 'Brito', 'Santana');")
                .executeUpdate();

        entityManager
                .createNativeQuery("insert into student (`registration`,`id`) values ('202000020815', '20000');", Student.class)
                .executeUpdate();

        entityManager
                .createNativeQuery("insert into person (`id`, `email`, `first_name`, `last_name`, `middle_name`, " +
                        "`social_first_name`, `social_last_name`, `social_middle_name`) " +
                        "values (20001, 'breno@email.com', 'Breno', 'Matos', 'Fontes', 'Breno', 'Matos', 'Fontes');")
                .executeUpdate();

        entityManager
                .createNativeQuery("insert into instructor (`specialty`,`id`) values ('Java', '20001');", Instructor.class)
                .executeUpdate();

        entityManager
                .createNativeQuery("insert into person (`id`, `email`, `first_name`, `last_name`, `middle_name`, " +
                        "`social_first_name`, `social_last_name`, `social_middle_name`) " +
                        "values (20002, 'jaqueline@email.com', 'Jaqueline', 'Félix', 'da Silva', 'Jaqueline', 'Félix', 'da Silva');")
                .executeUpdate();

        entityManager
                .createNativeQuery("insert into employee (`employee_role`,`id`) values ('TRAINEE', '20002');", Employee.class)
                .executeUpdate();
    }

    @AfterEach
    void tearDown() {
        entityManager.createNativeQuery("delete from student;").executeUpdate();
        entityManager.createNativeQuery("delete from instructor").executeUpdate();
        entityManager.createNativeQuery("delete from employee").executeUpdate();
        entityManager.createNativeQuery("delete from person;").executeUpdate();
    }

    @Test
    void persistPerson() {
        final Name name = new Name("Joana", "Barbosa", "Batista");
        final Name socialName = new Name("Joana", "Barbosa", "Batista");
        final Student student = new Student(name, socialName, "joana@email.com", "202000019500");

        personService.persistPerson(student);

        final List<Person> people = entityManager
                .createQuery("from Person order by id", Person.class)
                .getResultList();

        assertEquals(4, people.size());

        boolean containsJoana = false;
        for (Person person : people) {
            if (person.toString().equals("Joana Barbosa Batista (202000019500)")) {
                containsJoana = true;
                break;
            }
        }

        assertTrue(containsJoana);
    }

    @Test
    void getStudents() {
        final List<Person> students = personService.getStudents(0, 10);

        assertEquals(1, students.size());

        boolean containsJorge = false;
        for (Person student : students) {
            if (student.toString().equals("Jorge Santana Brito (202000020815)")) {
                containsJorge = true;
                break;
            }
        }

        assertTrue(containsJorge);
    }

    @Test
    void getInstructors() {
        final List<Person> instructors = personService.getInstructors(0, 10);

        assertEquals(1, instructors.size());

        boolean containsBreno = false;
        for (Person instructor : instructors) {
            if (instructor.toString().equals("Breno Fontes Matos (Java)")) {
                containsBreno = true;
                break;
            }
        }

        assertTrue(containsBreno);
    }

    @Test
    void getEmployees() {
        final List<Person> employees = personService.getEmployees(0, 10);

        assertEquals(1, employees.size());

        boolean containsJaqueline = false;
        for (Person employee : employees) {
            if (employee.toString().equals("Jaqueline da Silva Félix (TRAINEE)")) {
                containsJaqueline = true;
                break;
            }
        }

        assertTrue(containsJaqueline);
    }

    @Test
    void getStudentById() {
        final Student student = (Student) personService.getStudentById(20000);

        assertEquals("Jorge Santana Brito (202000020815)", student.toString());
        assertThrows(NoResultException.class, () -> personService.getStudentById(0));
    }

    @Test
    void getInstructorById() {
        final Instructor instructor = (Instructor) personService.getInstructorById(20001);

        assertEquals("Breno Fontes Matos (Java)", instructor.toString());
        assertThrows(NoResultException.class, () -> personService.getInstructorById(0));
    }

    @Test
    void getEmployeeById() {
        final Employee employee = (Employee) personService.getEmployeeById(20002);

        assertEquals("Jaqueline da Silva Félix (TRAINEE)", employee.toString());
        assertThrows(NoResultException.class, () -> personService.getEmployeeById(0));
    }
}