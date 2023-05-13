package augusto108.ces.advhibernate.services;

import augusto108.ces.advhibernate.domain.entities.Person;
import augusto108.ces.advhibernate.repositories.PersonRepository;
import jakarta.persistence.NoResultException;
import jakarta.transaction.Transactional;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class PersonServiceImpl implements PersonService {
    private final PersonRepository personRepository;

    public PersonServiceImpl(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @Override
    public void persistPerson(Person person) {
        personRepository.persistPerson(person);
    }

    @Override
    public List<Person> getStudents(int page, int max) {
        return personRepository.getStudents(page, max);
    }

    @Override
    public List<Person> getInstructors(int page, int max) {
        return personRepository.getInstructors(page, max);
    }

    @Override
    public List<Person> getEmployees(int page, int max) {
        return personRepository.getEmployees(page, max);
    }

    @Override
    public Person getStudentById(Integer id) {
        try {
            return personRepository.getStudentById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new NoResultException("No result found for id: " + id);
        }
    }

    @Override
    public Person getInstructorById(Integer id) {
        try {
            return personRepository.getInstructorById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new NoResultException("No result found for id: " + id);
        }
    }

    @Override
    public Person getEmployeeById(Integer id) {
        try {
            return personRepository.getEmployeeById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new NoResultException("No result found for id: " + id);
        }
    }
}
