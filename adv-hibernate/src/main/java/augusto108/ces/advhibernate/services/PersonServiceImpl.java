package augusto108.ces.advhibernate.services;

import augusto108.ces.advhibernate.domain.entities.Person;
import augusto108.ces.advhibernate.repositories.PersonRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

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
}
