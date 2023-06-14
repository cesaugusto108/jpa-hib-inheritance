package augusto108.ces.advhibernate.repositories;

import augusto108.ces.advhibernate.domain.entities.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@Transactional
@ActiveProfiles("test")
@DisplayNameGeneration(DisplayNameGenerator.Simple.class)
class PersonRepositoryImplTest {
    @Autowired
    private PersonRepository personRepository;

    @PersistenceContext
    private EntityManager entityManager;

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

    @Sql("/students-script.sql")
    @Test
    void getStudents() {
        final List<Student> studentList = personRepository.getStudents(0, 5);

        assertNotNull(studentList);

        assertEquals(2, studentList.size());
        assertEquals(100, studentList.get(0).getId());
        assertEquals(101, studentList.get(1).getId());

        assertEquals("Larissa", studentList.get(0).getName().getFirstName());
        assertEquals("Pereira", studentList.get(0).getName().getMiddleName());
        assertEquals("Castro", studentList.get(0).getName().getLastName());
        assertEquals("Larissa", studentList.get(0).getSocialName().getFirstName());
        assertEquals("Pereira", studentList.get(0).getSocialName().getMiddleName());
        assertEquals("Castro", studentList.get(0).getSocialName().getLastName());
        assertEquals("202000011201", studentList.get(0).getRegistration());

        assertEquals("Cláudia", studentList.get(1).getName().getFirstName());
        assertEquals("Vieira", studentList.get(1).getName().getMiddleName());
        assertEquals("Antunes", studentList.get(1).getName().getLastName());
        assertEquals("Cláudia", studentList.get(1).getSocialName().getFirstName());
        assertEquals("Vieira", studentList.get(1).getSocialName().getMiddleName());
        assertEquals("Antunes", studentList.get(1).getSocialName().getLastName());
        assertEquals("202000011202", studentList.get(1).getRegistration());
    }

    @Sql("/instructors-script.sql")
    @Test
    void getInstructors() {
        final List<Instructor> instructorList = personRepository.getInstructors(0, 5);

        assertNotNull(instructorList);

        assertEquals(2, instructorList.size());
        assertEquals(102, instructorList.get(0).getId());
        assertEquals(103, instructorList.get(1).getId());

        assertEquals("Milena", instructorList.get(0).getName().getFirstName());
        assertEquals("Freitas", instructorList.get(0).getName().getMiddleName());
        assertEquals("Andrade", instructorList.get(0).getName().getLastName());
        assertEquals("Milena", instructorList.get(0).getSocialName().getFirstName());
        assertEquals("Freitas", instructorList.get(0).getSocialName().getMiddleName());
        assertEquals("Andrade", instructorList.get(0).getSocialName().getLastName());

        assertEquals("Leonardo", instructorList.get(1).getName().getFirstName());
        assertEquals("Silva", instructorList.get(1).getName().getMiddleName());
        assertEquals("Ferreira", instructorList.get(1).getName().getLastName());
        assertEquals("Leonardo", instructorList.get(1).getSocialName().getFirstName());
        assertEquals("Silva", instructorList.get(1).getSocialName().getMiddleName());
        assertEquals("Ferreira", instructorList.get(1).getSocialName().getLastName());
    }

    @Sql("/employees-script.sql")
    @Test
    void getEmployees() {
        final List<Employee> employeeList = personRepository.getEmployees(0, 5);

        assertNotNull(employeeList);

        assertEquals(2, employeeList.size());
        assertEquals(104, employeeList.get(0).getId());
        assertEquals(105, employeeList.get(1).getId());

        assertEquals("Ronaldo", employeeList.get(0).getName().getFirstName());
        assertEquals("Oliveira", employeeList.get(0).getName().getMiddleName());
        assertEquals("Santos", employeeList.get(0).getName().getLastName());
        assertEquals("Ronaldo", employeeList.get(0).getSocialName().getFirstName());
        assertEquals("Oliveira", employeeList.get(0).getSocialName().getMiddleName());
        assertEquals("Santos", employeeList.get(0).getSocialName().getLastName());

        assertEquals("Lígia", employeeList.get(1).getName().getFirstName());
        assertEquals("Vieira", employeeList.get(1).getName().getMiddleName());
        assertEquals("Barros", employeeList.get(1).getName().getLastName());
        assertEquals("Lígia", employeeList.get(1).getSocialName().getFirstName());
        assertEquals("Vieira", employeeList.get(1).getSocialName().getMiddleName());
        assertEquals("Barros", employeeList.get(1).getSocialName().getLastName());
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