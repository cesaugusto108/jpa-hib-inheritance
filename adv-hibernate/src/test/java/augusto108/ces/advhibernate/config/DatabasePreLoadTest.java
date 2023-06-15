package augusto108.ces.advhibernate.config;

import augusto108.ces.advhibernate.data.EmployeeLoad;
import augusto108.ces.advhibernate.data.InstructorLoad;
import augusto108.ces.advhibernate.data.StudentLoad;
import augusto108.ces.advhibernate.domain.entities.Student;
import augusto108.ces.advhibernate.domain.entities.Telephone;
import augusto108.ces.advhibernate.services.PersonService;
import augusto108.ces.advhibernate.services.TelephoneService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

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

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
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
    }

    @Test
    void persistEmployee() {
    }
}