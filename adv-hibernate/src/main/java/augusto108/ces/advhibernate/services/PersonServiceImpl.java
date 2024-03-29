package augusto108.ces.advhibernate.services;

import augusto108.ces.advhibernate.domain.entities.Employee;
import augusto108.ces.advhibernate.domain.entities.Instructor;
import augusto108.ces.advhibernate.domain.entities.Person;
import augusto108.ces.advhibernate.domain.entities.Student;
import augusto108.ces.advhibernate.repositories.PersonRepository;
import jakarta.persistence.NoResultException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class PersonServiceImpl implements PersonService {

    private final PersonRepository personRepository;

    @Autowired
    public PersonServiceImpl(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @Override
    public void persistPerson(Person person) {
        personRepository.persistPerson(person);
    }

    @Override
    public List<Student> getStudents(int page, int max) {
        return personRepository.getStudents(page, max);
    }

    @Override
    public List<Instructor> getInstructors(int page, int max) {
        return personRepository.getInstructors(page, max);
    }

    @Override
    public List<Employee> getEmployees(int page, int max) {
        return personRepository.getEmployees(page, max);
    }

    @Override
    public Person getPersonById(Integer id) {
        try {
            return personRepository.getPersonById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new NoResultException("No result found for id: " + id);
        }
    }
}
