package augusto108.ces.advhibernate.services;

import augusto108.ces.advhibernate.domain.entities.Employee;
import augusto108.ces.advhibernate.domain.entities.Instructor;
import augusto108.ces.advhibernate.domain.entities.Person;
import augusto108.ces.advhibernate.domain.entities.Student;

import java.util.List;

public interface PersonService {
    void persistStudent(Student student);

    void persistInstructor(Instructor instructor);

    void persistEmployee(Employee employee);

    List<Student> getStudents(int page, int max);

    List<Instructor> getInstructors(int page, int max);

    List<Employee> getEmployees(int page, int max);

    Person getStudentById(Integer id);

    Person getInstructorById(Integer id);

    Person getEmployeeById(Integer id);
}
