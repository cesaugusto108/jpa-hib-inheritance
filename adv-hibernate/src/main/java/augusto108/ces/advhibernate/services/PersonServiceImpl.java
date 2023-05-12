package augusto108.ces.advhibernate.services;

import augusto108.ces.advhibernate.domain.entities.Person;
import augusto108.ces.advhibernate.repositories.PersonRepository;
import jakarta.transaction.Transactional;
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
}
