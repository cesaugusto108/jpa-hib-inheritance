package augusto108.ces.advhibernate.repositories;

import augusto108.ces.advhibernate.domain.entities.Telephone;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Repository;

@Repository
public class TelephoneRepositoryImpl implements TelephoneRepository {
    private final EntityManager entityManager;

    public TelephoneRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public void persistTelephone(Telephone telephone) {
        entityManager.persist(telephone);
    }
}
