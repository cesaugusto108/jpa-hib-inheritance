package augusto108.ces.advhibernate.services;

import augusto108.ces.advhibernate.domain.entities.Instructor;
import augusto108.ces.advhibernate.domain.entities.Person;
import augusto108.ces.advhibernate.domain.entities.Student;

import java.util.List;

public interface PersonService {
    void persistPerson(Person person);

    List<Student> getStudents(int page, int max);

    List<Instructor> getInstructors(int page, int max);

    List<Person> getEmployees(int page, int max);

    Person getPersonById(Integer id);
}
