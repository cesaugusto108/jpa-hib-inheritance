package augusto108.ces.advhibernate.repositories;

import augusto108.ces.advhibernate.domain.entities.Person;

public interface PersonRepository {
    void persistPerson(Person person);
}
