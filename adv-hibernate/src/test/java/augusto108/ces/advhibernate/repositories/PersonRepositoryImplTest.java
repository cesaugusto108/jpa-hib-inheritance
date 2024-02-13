package augusto108.ces.advhibernate.repositories;

import augusto108.ces.advhibernate.domain.entities.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@ActiveProfiles("test")
@DisplayNameGeneration(DisplayNameGenerator.Simple.class)
class PersonRepositoryImplTest {

    private final PersonRepository personRepository;

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    PersonRepositoryImplTest(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @Test
    void persistPerson() {
        final Name studentName = new Name("Maria", "Santos", "Andrade");
        final Name socialStudentName = new Name("Maria", "Santos", "Andrade");
        final Person student = new Student(studentName, socialStudentName, "maria@email.com", "202200022002");

        personRepository.persistPerson(student);

        final Person persistedPerson = entityManager
                .createQuery("from Person p where registration = :registration and email = :email", Person.class)
                .setParameter("registration", "202200022002")
                .setParameter("email", "maria@email.com")
                .getSingleResult();

        assertNotNull(persistedPerson);
        assertEquals("Maria", persistedPerson.getName().getFirstName());
        assertEquals("Santos", persistedPerson.getName().getMiddleName());
        assertEquals("Andrade", persistedPerson.getName().getLastName());
        assertEquals("Maria", persistedPerson.getSocialName().getFirstName());
        assertEquals("Santos", persistedPerson.getSocialName().getMiddleName());
        assertEquals("Andrade", persistedPerson.getSocialName().getLastName());
        assertEquals("maria@email.com", persistedPerson.getEmail());
    }

    @Sql("/students-script.sql")
    @Test
    void getStudents() {
        final List<Student> studentList = personRepository.getStudents(0, 10);

        assertNotNull(studentList);

        boolean containsLarissa = false;
        for (Student student : studentList) {
            if (student.toString().equals("Larissa Pereira Castro (202000011201)")) {
                containsLarissa = true;
                break;
            }
        }

        boolean containsClaudia = false;
        for (Student student : studentList) {
            if (student.toString().equals("Cláudia Vieira Antunes (202000011202)")) {
                containsClaudia = true;
                break;
            }
        }

        assertTrue(containsLarissa && containsClaudia);
    }

    @Sql("/instructors-script.sql")
    @Test
    void getInstructors() {
        final List<Instructor> instructorList = personRepository.getInstructors(0, 5);

        assertNotNull(instructorList);

        boolean containsMilena = false;
        for (Instructor instructor : instructorList) {
            if (instructor.toString().equals("Milena Freitas Andrade (Java)")) {
                containsMilena = true;
                break;
            }
        }

        boolean containsLeonardo = false;
        for (Instructor instructor : instructorList) {
            if (instructor.toString().equals("Leonardo Silva Ferreira (Kotlin)")) {
                containsLeonardo = true;
                break;
            }
        }

        assertTrue(containsMilena && containsLeonardo);
    }

    @Sql("/employees-script.sql")
    @Test
    void getEmployees() {
        final List<Employee> employeeList = personRepository.getEmployees(0, 5);

        assertNotNull(employeeList);

        boolean containsRonaldo = false;
        for (Employee employee : employeeList) {
            if (employee.toString().equals("Ronaldo Oliveira Santos (TRAINEE)")) {
                containsRonaldo = true;
                break;
            }
        }

        boolean containsLigia = false;
        for (Employee employee : employeeList) {
            if (employee.toString().equals("Lígia Vieira Barros (ADMIN)")) {
                containsLigia = true;
                break;
            }
        }

        assertTrue(containsRonaldo && containsLigia);
    }

    @Sql({"/students-script.sql", "/instructors-script.sql", "/employees-script.sql"})
    @Test
    void getPersonById() {
        final Student larissa = (Student) personRepository.getPersonById(100);
        final Student claudia = (Student) personRepository.getPersonById(101);
        final Instructor milena = (Instructor) personRepository.getPersonById(102);
        final Instructor leonardo = (Instructor) personRepository.getPersonById(103);
        final Employee ronaldo = (Employee) personRepository.getPersonById(104);
        final Employee ligia = (Employee) personRepository.getPersonById(105);

        assertNotNull(larissa);
        assertNotNull(claudia);
        assertNotNull(milena);
        assertNotNull(leonardo);
        assertNotNull(ronaldo);
        assertNotNull(ligia);

        assertEquals("202000011201", larissa.getRegistration());
        assertEquals("202000011202", claudia.getRegistration());
        assertEquals("Java", milena.getSpecialty());
        assertEquals("Kotlin", leonardo.getSpecialty());
        assertEquals("TRAINEE", ronaldo.getRole().toString());
        assertEquals("ADMIN", ligia.getRole().toString());
    }
}