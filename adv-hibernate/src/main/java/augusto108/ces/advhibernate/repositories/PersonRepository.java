package augusto108.ces.advhibernate.repositories;

import augusto108.ces.advhibernate.domain.entities.Employee;
import augusto108.ces.advhibernate.domain.entities.Instructor;
import augusto108.ces.advhibernate.domain.entities.Person;
import augusto108.ces.advhibernate.domain.entities.Student;

import java.util.List;

public interface PersonRepository {
    void persistPerson(Person person);

    List<Student> getStudents(int page, int max);

    List<Instructor> getInstructors(int page, int max);

    List<Employee> getEmployees(int page, int max);

    Person getPersonById(Integer id);
}
