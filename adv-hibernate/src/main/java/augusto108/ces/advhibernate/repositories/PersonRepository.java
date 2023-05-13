package augusto108.ces.advhibernate.repositories;

import augusto108.ces.advhibernate.domain.entities.Person;

import java.util.List;

public interface PersonRepository {
    void persistPerson(Person person);

    List<Person> getStudents(int page, int max);

    List<Person> getInstructors(int page, int max);

    List<Person> getEmployees(int page, int max);

    Person getStudentById(Integer id);

    Person getInstructorById(Integer id);

    Person getEmployeeById(Integer id);
}
