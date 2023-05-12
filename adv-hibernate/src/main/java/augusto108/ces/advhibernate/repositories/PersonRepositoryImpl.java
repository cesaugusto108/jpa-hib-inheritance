package augusto108.ces.advhibernate.repositories;

import augusto108.ces.advhibernate.domain.entities.Person;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Repository;

@Repository
public class PersonRepositoryImpl implements PersonRepository {
    private final EntityManager entityManager;

    public PersonRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public void persistPerson(Person person) {
        entityManager.persist(person);
    }
}
