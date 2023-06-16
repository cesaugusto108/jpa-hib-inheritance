package augusto108.ces.advhibernate.repositories;

import augusto108.ces.advhibernate.domain.entities.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@ActiveProfiles("test")
class PersonRepositoryImplTest {
    @Autowired
    private PersonRepository personRepository;

    @PersistenceContext
    private EntityManager entityManager;

    @BeforeEach
    void setUp() {
        entityManager
                .createNativeQuery("insert into person (`id`, `email`, `first_name`, `last_name`, `middle_name`, " +
                        "`social_first_name`, `social_last_name`, `social_middle_name`) " +
                        "values (10000, 'tania@email.com', 'Tânia', 'Brito', 'Almeida', 'Tânia', 'Brito', 'Almeida');")
                .executeUpdate();

        entityManager
                .createNativeQuery("insert into student (`registration`,`id`) values ('202000020800', '10000');", Student.class)
                .executeUpdate();

        entityManager
                .createNativeQuery("insert into person (`id`, `email`, `first_name`, `last_name`, `middle_name`, " +
                        "`social_first_name`, `social_last_name`, `social_middle_name`) " +
                        "values (10001, 'jose@email.com', 'José', 'Silva', 'Adriano', 'José', 'Silva', 'Adriano');")
                .executeUpdate();

        entityManager
                .createNativeQuery("insert into instructor (`specialty`,`id`) values ('Redes', '10001');", Instructor.class)
                .executeUpdate();

        entityManager
                .createNativeQuery("insert into person (`id`, `email`, `first_name`, `last_name`, `middle_name`, " +
                        "`social_first_name`, `social_last_name`, `social_middle_name`) " +
                        "values (10002, 'daniel@email.com', 'Daniel', 'Santos', 'Lima', 'Daniel', 'Santos', 'Lima');")
                .executeUpdate();

        entityManager
                .createNativeQuery("insert into employee (`employee_role`,`id`) values ('ADMIN', '10002');", Employee.class)
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
        final Name name = new Name("Carla", "Souza", "Andrade");
        final Name socialName = new Name("Carla", "Souza", "Andrade");
        final Student student = new Student(name, socialName, "carla@email.com", "202000019900");

        assertNotNull(student);

        personRepository.persistPerson(student);

        final List<Person> people = entityManager
                .createQuery("from Person order by id", Person.class)
                .getResultList();

        assertEquals(4, people.size());

        boolean containsCarla = false;
        for (Person person : people) {
            if (person.toString().equals("Carla Souza Andrade (202000019900)")) {
                containsCarla = true;
                break;
            }
        }

        assertTrue(containsCarla);
    }

    @Test
    void getStudents() {
        final List<Person> students = personRepository.getStudents(0, 10);

        assertEquals(1, students.size());

        boolean containsTania = false;
        for (Person student : students) {
            if (student.toString().equals("Tânia Almeida Brito (202000020800)")) {
                containsTania = true;
                break;
            }
        }

        assertTrue(containsTania);
    }

    @Test
    void getInstructors() {
        final List<Person> instructors = personRepository.getInstructors(0, 10);

        assertEquals(1, instructors.size());

        boolean containsJose = false;
        for (Person instructor : instructors) {
            if (instructor.toString().equals("José Adriano Silva (Redes)")) {
                containsJose = true;
                break;
            }
        }

        assertTrue(containsJose);
    }

    @Test
    void getEmployees() {
        final List<Person> employees = personRepository.getEmployees(0, 10);

        assertEquals(1, employees.size());

        boolean containsDaniel = false;
        for (Person employee : employees) {
            if (employee.toString().equals("Daniel Lima Santos (ADMIN)")) {
                containsDaniel = true;
                break;
            }
        }

        assertTrue(containsDaniel);
    }

    @Test
    void getStudentById() {
        final Student student = (Student) personRepository.getStudentById(10000);

        assertEquals("Tânia Almeida Brito (202000020800)", student.toString());
    }

    @Test
    void getInstructorById() {
        final Instructor instructor = (Instructor) personRepository.getInstructorById(10001);

        assertEquals("José Adriano Silva (Redes)", instructor.toString());
    }

    @Test
    void getEmployeeById() {
        final Employee employee = (Employee) personRepository.getEmployeeById(10002);

        assertEquals("Daniel Lima Santos (ADMIN)", employee.toString());
    }
}