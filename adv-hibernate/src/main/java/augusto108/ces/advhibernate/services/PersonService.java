package augusto108.ces.advhibernate.services;

import augusto108.ces.advhibernate.domain.entities.Person;

import java.util.List;

public interface PersonService {
    void persistPerson(Person person);

    List<Person> getPersons(int page, int max);
}
