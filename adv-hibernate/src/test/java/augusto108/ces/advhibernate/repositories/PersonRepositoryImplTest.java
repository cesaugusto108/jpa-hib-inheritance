package augusto108.ces.advhibernate.repositories;

import augusto108.ces.advhibernate.domain.entities.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Transactional
@ActiveProfiles("test")
@DisplayNameGeneration(DisplayNameGenerator.IndicativeSentences.class)
class PersonRepositoryImplTest {
    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @PersistenceContext
    private EntityManager entityManager;

    @BeforeEach
    void setUp() {
        final String insertStudent = "insert into student (`id`, `email`, `first_name`, `last_name`, `middle_name`," +
                "`social_first_name`, `social_last_name`, `social_middle_name`, `registration`) " +
                "values (10000, 'renata@email.com', 'Renata', 'Silva', 'Santos', 'Renata', 'Silva', 'Santos', '201900098191');";

        final String insertInstructor = "insert into instructor (`id`, `email`, `first_name`, `last_name`, `middle_name`," +
                "`social_first_name`, `social_last_name`, `social_middle_name`, `specialty`) " +
                "values (10001, 'amauri@email.com', 'Amauri', 'Andrade', 'Pereira', 'Amauri', 'Andrade', 'Pereira', 'Banco de Dados');";

        final String insertEmployee = "insert into employee (`id`, `email`, `first_name`, `last_name`, `middle_name`," +
                "`social_first_name`, `social_last_name`, `social_middle_name`, `employee_role`) " +
                "values (10002, 'patricia@email.com', 'Patrícia', 'Castro', 'Silva', 'Patrícia', 'Castro', 'Silva', 'MANAGER');";

        jdbcTemplate.execute(insertStudent);
        jdbcTemplate.execute(insertInstructor);
        jdbcTemplate.execute(insertEmployee);
    }

    @AfterEach
    void tearDown() {
        jdbcTemplate.execute("delete from student;");
        jdbcTemplate.execute("delete from instructor;");
        jdbcTemplate.execute("delete from employee;");
    }

    @Test
    void persistPerson() {
        final Name name = new Name("Rita", "de Cássia", "Moura");
        final Name socialName = new Name("Rita", "de Cássia", "Moura");
        final Student student = new Student(name, socialName, "rita@email.com", "201900079181");

        personRepository.persistPerson(student);

        final List<Student> students = entityManager
                .createQuery("from Student order by id", Student.class)
                .getResultList();

        assertEquals(2, students.size());
        assertEquals("Rita de Cássia Moura (201900079181)", students.get(0).toString());
        assertEquals("Renata Santos Silva (201900098191)", students.get(1).toString());
    }

    @Test
    void getStudents() {
        final List<Person> students = personRepository.getStudents(0, 10);

        assertEquals(1, students.size());
        assertEquals("Renata Santos Silva (201900098191)", students.get(0).toString());
    }

    @Test
    void getInstructors() {
        final List<Person> instructors = personRepository.getInstructors(0, 10);

        assertEquals(1, instructors.size());
        assertEquals("Amauri Pereira Andrade (Banco de Dados)", instructors.get(0).toString());
    }

    @Test
    void getEmployees() {
        final List<Person> employees = personRepository.getEmployees(0, 10);

        assertEquals(1, employees.size());
        assertEquals("Patrícia Silva Castro (MANAGER)", employees.get(0).toString());
    }

    @Test
    void getStudentById() {
        final Student student = (Student) personRepository.getStudentById(10000);
        assertEquals("Renata Santos Silva (201900098191)", student.toString());
    }

    @Test
    void getInstructorById() {
        final Instructor instructor = (Instructor) personRepository.getInstructorById(10001);
        assertEquals("Amauri Pereira Andrade (Banco de Dados)", instructor.toString());
    }

    @Test
    void getEmployeeById() {
        final Employee employee = (Employee) personRepository.getEmployeeById(10002);
        assertEquals("Patrícia Silva Castro (MANAGER)", employee.toString());
    }
}