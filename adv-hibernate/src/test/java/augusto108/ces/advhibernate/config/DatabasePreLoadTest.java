package augusto108.ces.advhibernate.config;

import augusto108.ces.advhibernate.data.EmployeeLoad;
import augusto108.ces.advhibernate.data.InstructorLoad;
import augusto108.ces.advhibernate.data.StudentLoad;
import augusto108.ces.advhibernate.domain.entities.Employee;
import augusto108.ces.advhibernate.domain.entities.Instructor;
import augusto108.ces.advhibernate.domain.entities.Student;
import augusto108.ces.advhibernate.domain.entities.Telephone;
import augusto108.ces.advhibernate.domain.entities.enums.Role;
import augusto108.ces.advhibernate.services.PersonService;
import augusto108.ces.advhibernate.services.TelephoneService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.*;
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

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @AfterEach
    void tearDown() {
        jdbcTemplate.execute("delete from person_telephone;");
        jdbcTemplate.execute("delete from student;");
        jdbcTemplate.execute("delete from instructor;");
        jdbcTemplate.execute("delete from employee;");
        jdbcTemplate.execute("delete from person;");
        jdbcTemplate.execute("delete from telephone;");
    }

    @Test
    void persistStudent() {
        final Telephone telephone = studentLoad.createStudentTelephone();
        final Student student = studentLoad.createStudent();

        assertNotNull(telephone);
        assertNotNull(student);

        telephoneService.persistTelephone(telephone);
        student.getTelephones().add(telephone);
        personService.persistPerson(student);

        final Student s = entityManager
                .createQuery("from Student where registration = :registration and email = :email", Student.class)
                .setParameter("registration", "202000089000")
                .setParameter("email", "carlos@email.com")
                .getSingleResult();

        assertEquals("202000089000", s.getRegistration());
        assertEquals("carlos@email.com", s.getEmail());

        final Telephone t = (Telephone) entityManager
                .createQuery("from Telephone t where number = :number", Telephone.class)
                .setParameter("number", "999990000")
                .getSingleResult();

        assertEquals(t.getAreaCode(), "71");
        assertEquals(t.getNumber(), "999990000");

        assertEquals(1, s.getTelephones().size());
        assertEquals(t, s.getTelephones().toArray()[0]);
    }

    @Test
    void persistInstructor() {
        final Telephone[] telephones = instructorLoad.createTelephones();
        final Instructor instructor = instructorLoad.createInstructor();

        assertNotNull(telephones);
        assertNotNull(instructor);

        for (Telephone telephone : telephones) {
            telephoneService.persistTelephone(telephone);
        }

        instructor.getTelephones().addAll(List.of(telephones));
        personService.persistPerson(instructor);

        final Instructor i = entityManager
                .createQuery("from Instructor i where specialty = :specialty and email = :email", Instructor.class)
                .setParameter("specialty", "Redes")
                .setParameter("email", "antonio@email.com")
                .getSingleResult();

        assertEquals("Redes", i.getSpecialty());
        assertEquals("antonio@email.com", i.getEmail());

        final Telephone t1 = entityManager
                .createQuery("from Telephone t where number = :number", Telephone.class)
                .setParameter("number", "999999999")
                .getSingleResult();

        final Telephone t2 = entityManager
                .createQuery("from Telephone t where number = :number", Telephone.class)
                .setParameter("number", "999991111")
                .getSingleResult();

        assertEquals(t1.getAreaCode(), "79");
        assertEquals(t1.getNumber(), "999999999");
        assertEquals(t2.getAreaCode(), "79");
        assertEquals(t2.getNumber(), "999991111");

        assertEquals(2, i.getTelephones().size());
        assertEquals(t1, i.getTelephones().toArray()[0]);
        assertEquals(t2, i.getTelephones().toArray()[1]);
    }

    @Test
    void persistEmployee() {
        final Telephone[] telephones = employeeLoad.createTelephones();
        final Employee employee = employeeLoad.createEmployee();

        assertNotNull(telephones);
        assertNotNull(employee);

        for (Telephone telephone : telephones) {
            telephoneService.persistTelephone(telephone);
        }

        employee.getTelephones().addAll(List.of(telephones));
        personService.persistPerson(employee);

        final Employee e = entityManager
                .createQuery("from Employee e where role = :role and email = :email", Employee.class)
                .setParameter("role", Role.ADMIN)
                .setParameter("email", "marieta@email.com")
                .getSingleResult();

        assertEquals("ADMIN", e.getRole().toString());
        assertEquals("marieta@email.com", e.getEmail());

        final Telephone t1 = entityManager
                .createQuery("from Telephone t where number = :number", Telephone.class)
                .setParameter("number", "999993333")
                .getSingleResult();

        final Telephone t2 = entityManager
                .createQuery("from Telephone t where number = :number", Telephone.class)
                .setParameter("number", "999994444")
                .getSingleResult();

        assertEquals(t1.getAreaCode(), "79");
        assertEquals(t1.getNumber(), "999993333");
        assertEquals(t2.getAreaCode(), "79");
        assertEquals(t2.getNumber(), "999994444");

        assertEquals(2, e.getTelephones().size());
        assertEquals(t1, e.getTelephones().toArray()[0]);
        assertEquals(t2, e.getTelephones().toArray()[1]);
    }

    @Nested
    @DisplayNameGeneration(DisplayNameGenerator.IndicativeSentences.class)
    class DatabasePreLoadClassNestedTests {
        private DatabasePreLoad databasePreLoad;

        @BeforeEach
        void setUp() {
            databasePreLoad =
                    new DatabasePreLoad(personService, telephoneService, studentLoad, instructorLoad, employeeLoad);
        }

        @Test
        void persistStudent() {
            databasePreLoad.persistStudent();

            final List<Student> students = entityManager
                    .createQuery("from Person order by id", Student.class)
                    .getResultList();

            assertEquals(1, students.size());
            assertEquals("carlos@email.com", students.get(0).getEmail());

            final Telephone telephone = (Telephone) students.get(0).getTelephones().toArray()[0];

            assertEquals(
                    new Telephone("55", "71", "999990000").toString(),
                    telephone.toString()
            );
        }

        @Test
        void persistInstructor() {
            databasePreLoad.persistInstructor();

            final List<Instructor> instructors = entityManager
                    .createQuery("from Person order by id", Instructor.class)
                    .getResultList();

            assertEquals(1, instructors.size());
            assertEquals("antonio@email.com", instructors.get(0).getEmail());

            final Telephone telephone1 = (Telephone) instructors.get(0).getTelephones().toArray()[0];
            final Telephone telephone2 = (Telephone) instructors.get(0).getTelephones().toArray()[1];

            assertEquals(
                    new Telephone("55", "79", "999999999").toString(),
                    telephone1.toString()
            );

            assertEquals(
                    new Telephone("55", "79", "999991111").toString(),
                    telephone2.toString()
            );
        }

        @Test
        void persistEmployee() {
            databasePreLoad.persistEmployee();

            final List<Employee> employees = entityManager
                    .createQuery("from Person order by id", Employee.class)
                    .getResultList();

            assertEquals(1, employees.size());
            assertEquals("marieta@email.com", employees.get(0).getEmail());

            final Telephone telephone1 = (Telephone) employees.get(0).getTelephones().toArray()[0];
            final Telephone telephone2 = (Telephone) employees.get(0).getTelephones().toArray()[1];

            assertEquals(
                    new Telephone("55", "79", "999993333").toString(),
                    telephone1.toString()
            );

            assertEquals(
                    new Telephone("55", "79", "999994444").toString(),
                    telephone2.toString()
            );
        }
    }
}