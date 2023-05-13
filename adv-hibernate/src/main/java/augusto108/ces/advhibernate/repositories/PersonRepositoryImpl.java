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
    public void persistStudent(Student student) {
        entityManager.persist(student);
    }

    @Override
    public void persistInstructor(Instructor instructor) {
        entityManager.persist(instructor);
    }

    @Override
    public void persistEmployee(Employee employee) {
        entityManager.persist(employee);
    }

    @Override
    public List<Student> getStudents(int page, int max) {
        return entityManager
                .createQuery("from Student", Student.class)
                .setFirstResult(page * max)
                .setMaxResults(max)
                .getResultList();
    }

    @Override
    public List<Instructor> getInstructors(int page, int max) {
        return entityManager
                .createQuery("from Instructor", Instructor.class)
                .setFirstResult(page * max)
                .setMaxResults(max)
                .getResultList();
    }

    @Override
    public List<Employee> getEmployees(int page, int max) {
        return entityManager
                .createQuery("from Employee", Employee.class)
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
