package augusto108.ces.advhibernate.repositories;

import augusto108.ces.advhibernate.domain.entities.Employee;
import augusto108.ces.advhibernate.domain.entities.Instructor;
import augusto108.ces.advhibernate.domain.entities.Person;
import augusto108.ces.advhibernate.domain.entities.Student;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PersonRepositoryImpl implements PersonRepository {
    private final EntityManager entityManager;

    public PersonRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public void persistPerson(Person person) {
        entityManager.persist(person);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Student> getStudents(int page, int max) {
        return entityManager
                .createNativeQuery("select * from person where person_type = 'student'", Student.class)
                .setFirstResult(page * max)
                .setMaxResults(max)
                .getResultList();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Person> getInstructors(int page, int max) {
        return entityManager
                .createNativeQuery("select * from person where person_type = 'instructor'", Instructor.class)
                .setFirstResult(page * max)
                .setMaxResults(max)
                .getResultList();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Person> getEmployees(int page, int max) {
        return entityManager
                .createNativeQuery("select * from person where person_type = 'employee'", Employee.class)
                .setFirstResult(page * max)
                .setMaxResults(max)
                .getResultList();
    }

    @Override
    public Person getPersonById(Integer id) {
        return entityManager
                .createQuery("from Person p where id = :id", Person.class)
                .setParameter("id", id)
                .getSingleResult();
    }
}
