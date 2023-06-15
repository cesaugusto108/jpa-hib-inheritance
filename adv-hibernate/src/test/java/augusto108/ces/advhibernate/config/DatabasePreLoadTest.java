package augusto108.ces.advhibernate.config;

import augusto108.ces.advhibernate.data.EmployeeLoad;
import augusto108.ces.advhibernate.data.InstructorLoad;
import augusto108.ces.advhibernate.data.StudentLoad;
import augusto108.ces.advhibernate.domain.entities.Instructor;
import augusto108.ces.advhibernate.domain.entities.Student;
import augusto108.ces.advhibernate.domain.entities.Telephone;
import augusto108.ces.advhibernate.services.PersonService;
import augusto108.ces.advhibernate.services.TelephoneService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.AfterEach;
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

        final Telephone t1 = (Telephone) entityManager
                .createQuery("from Telephone t where number = :number", Telephone.class)
                .setParameter("number", "999999999")
                .getSingleResult();

        final Telephone t2 = (Telephone) entityManager
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
    }
}