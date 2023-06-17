package augusto108.ces.advhibernate.services;

import augusto108.ces.advhibernate.domain.entities.*;
import augusto108.ces.advhibernate.domain.entities.enums.Role;
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
@DisplayNameGeneration(DisplayNameGenerator.Simple.class)
class PersonServiceImplTest {
    @Autowired
    private PersonService personService;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @PersistenceContext
    private EntityManager entityManager;

    @BeforeEach
    void setUp() {
        final String insertStudent = "insert into student (`id`, `email`, `first_name`, `last_name`, `middle_name`," +
                " `social_first_name`, `social_last_name`, `social_middle_name`, `registration`) " +
                "values (10000, 'felipe@email.com', 'Felipe', 'Monteiro', 'Pires', 'Felipe', 'Monteiro', 'Pires', '201900098666');";

        final String insertInstructor = "insert into instructor (`id`, `email`, `first_name`, `last_name`, `middle_name`," +
                " `social_first_name`, `social_last_name`, `social_middle_name`, `specialty`) " +
                "values (10001, 'amauri@email.com', 'Cátia', 'Almeida', 'Santos', 'Cátia', 'Almeida', 'Santos', 'Banco de Dados');";

        final String insertEmployee = "insert into employee (`id`, `email`, `first_name`, `last_name`, `middle_name`," +
                " `social_first_name`, `social_last_name`, `social_middle_name`, `employee_role`) " +
                "values (10002, 'otavio@email.com', 'Otávio', 'Brito', 'Silva', 'Otávio', 'Brito', 'Silva', 'MANAGER');";

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
        final Name name = new Name("Maria", "Clara", "Soares");
        final Name socialName = new Name("Maria", "Clara", "Soares");
        final Employee employee = new Employee(name, socialName, "maria@email.com", Role.MANAGER);

        personService.persistPerson(employee);

        final List<Employee> employees = entityManager
                .createQuery("from Employee order by id", Employee.class)
                .getResultList();

        assertEquals(2, employees.size());
        assertEquals("Maria Clara Soares (MANAGER)", employees.get(0).toString());
        assertEquals("Otávio Silva Brito (MANAGER)", employees.get(1).toString());
    }

    @Test
    void getStudents() {
        final List<Person> students = personService.getStudents(0, 10);
        assertEquals(1, students.size());
        assertEquals("Felipe Pires Monteiro (201900098666)", students.get(0).toString());
    }

    @Test
    void getInstructors() {
        final List<Person> instructors = personService.getInstructors(0, 10);
        assertEquals(1, instructors.size());
        assertEquals("Cátia Santos Almeida (Banco de Dados)", instructors.get(0).toString());
    }

    @Test
    void getEmployees() {
        final List<Person> employees = personService.getEmployees(0, 10);
        assertEquals(1, employees.size());
        assertEquals("Otávio Silva Brito (MANAGER)", employees.get(0).toString());
    }

    @Test
    void getStudentById() {
        final Student student = (Student) personService.getStudentById(10000);
        assertEquals(10000, student.getId());
        assertEquals("Felipe Pires Monteiro (201900098666)", student.toString());
    }

    @Test
    void getInstructorById() {
        final Instructor instructor = (Instructor) personService.getInstructorById(10001);
        assertEquals(10001, instructor.getId());
        assertEquals("Cátia Santos Almeida (Banco de Dados)", instructor.toString());
    }

    @Test
    void getEmployeeById() {
        final Employee employee = (Employee) personService.getEmployeeById(10002);
        assertEquals(10002, employee.getId());
        assertEquals("Otávio Silva Brito (MANAGER)", employee.toString());
    }
}