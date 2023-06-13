package augusto108.ces.advhibernate.repositories;

import augusto108.ces.advhibernate.domain.entities.Telephone;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class TelephoneRepositoryImpl implements TelephoneRepository {
    private final EntityManager entityManager;

    public TelephoneRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public Telephone getTelephoneById(Integer id) {
        return entityManager
                .createQuery("from Telephone t where id = :id", Telephone.class)
                .setParameter("id", id)
                .getSingleResult();
    }

    @Override
    public List<Telephone> getTelephones(int page, int max) {
        return entityManager
                .createQuery("from Telephone order by id", Telephone.class)
                .setFirstResult(page * max)
                .setMaxResults(max)
                .getResultList();
    }

    @Override
    public void persistTelephone(Telephone telephone) {
        entityManager.persist(telephone);
    }
}
