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
    public List<Person> getStudents(int page, int max) {
        return entityManager
                .createNativeQuery("select * from student order by first_name asc", Student.class)
                .setFirstResult(page * max)
                .setMaxResults(max)
                .getResultList();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Person> getInstructors(int page, int max) {
        return entityManager
                .createNativeQuery("select * from instructor order by first_name asc", Instructor.class)
                .setFirstResult(page * max)
                .setMaxResults(max)
                .getResultList();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Person> getEmployees(int page, int max) {
        return entityManager
                .createNativeQuery("select * from employee order by first_name asc", Employee.class)
                .setFirstResult(page * max)
                .setMaxResults(max)
                .getResultList();
    }

    @Override
    public Person getStudentById(Integer id) {
        return entityManager
                .createQuery("from Student p where id = :id", Student.class)
                .setParameter("id", id)
                .getSingleResult();
    }

    @Override
    public Person getInstructorById(Integer id) {
        return entityManager
                .createQuery("from Instructor p where id = :id", Instructor.class)
                .setParameter("id", id)
                .getSingleResult();
    }

    @Override
    public Person getEmployeeById(Integer id) {
        return entityManager
                .createQuery("from Employee p where id = :id", Employee.class)
                .setParameter("id", id)
                .getSingleResult();
    }
}
