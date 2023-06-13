package augusto108.ces.advhibernate.repositories;

import augusto108.ces.advhibernate.domain.entities.Name;
import augusto108.ces.advhibernate.domain.entities.Person;
import augusto108.ces.advhibernate.domain.entities.Student;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@ActiveProfiles("test")
@DisplayNameGeneration(DisplayNameGenerator.Simple.class)
class PersonRepositoryImplTest {
    @Autowired
    private PersonRepository personRepository;

    @PersistenceContext
    private EntityManager entityManager;

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void persistPerson() {
        final Name studentName = new Name("Maria", "Santos", "Andrade");
        final Name socialStudentName = new Name("Maria", "Santos", "Andrade");
        final Person student = new Student(studentName, socialStudentName, "maria@email.com", "202200022002");

        personRepository.persistPerson(student);

        final Person persistedPerson = entityManager
                .createQuery("from Person p where id = :id", Person.class)
                .setParameter("id", 1)
                .getSingleResult();

        assertNotNull(persistedPerson);
        assertEquals(1, persistedPerson.getId());
        assertEquals("Maria", persistedPerson.getName().getFirstName());
        assertEquals("Santos", persistedPerson.getName().getMiddleName());
        assertEquals("Andrade", persistedPerson.getName().getLastName());
        assertEquals("Maria", persistedPerson.getSocialName().getFirstName());
        assertEquals("Santos", persistedPerson.getSocialName().getMiddleName());
        assertEquals("Andrade", persistedPerson.getSocialName().getLastName());
        assertEquals("maria@email.com", persistedPerson.getEmail());
    }

    @Test
    void getStudents() {
    }

    @Test
    void getInstructors() {
    }

    @Test
    void getEmployees() {
    }

    @Test
    void getPersonById() {
    }
}