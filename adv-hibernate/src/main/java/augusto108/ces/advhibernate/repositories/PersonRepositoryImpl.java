package augusto108.ces.advhibernate.repositories;

import augusto108.ces.advhibernate.domain.entities.Person;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Repository;

import java.util.List;

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

    @SuppressWarnings("unchecked")
    @Override
    public List<Person> getPersons(int page, int max) {
        return entityManager.createNativeQuery("select * from person where person_type = 'student'", Person.class)
                .setFirstResult(page * max)
                .setMaxResults(max)
                .getResultList();
    }
}
