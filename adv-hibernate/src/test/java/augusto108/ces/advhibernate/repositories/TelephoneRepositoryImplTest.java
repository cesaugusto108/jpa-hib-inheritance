package augusto108.ces.advhibernate.repositories;

import augusto108.ces.advhibernate.domain.entities.Telephone;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@Transactional
@ActiveProfiles("test")
@DisplayNameGeneration(DisplayNameGenerator.Simple.class)
class TelephoneRepositoryImplTest {
    @Autowired
    private TelephoneRepository telephoneRepository;

    @PersistenceContext
    private EntityManager entityManager;

    @Sql("/telephones-script.sql")
    @Test
    void getTelephoneById() {
        final Telephone t1 = telephoneRepository.getTelephoneById(100);
        final Telephone t2 = telephoneRepository.getTelephoneById(101);
        final Telephone t3 = telephoneRepository.getTelephoneById(102);

        assertNotNull(t1);
        assertNotNull(t2);
        assertNotNull(t3);

        assertEquals(100, t1.getId());
        assertEquals(101, t2.getId());
        assertEquals(102, t3.getId());
        assertEquals("988080808", t1.getNumber());
        assertEquals("988080809", t2.getNumber());
        assertEquals("988080810", t3.getNumber());
    }

    @Sql("/telephones-script.sql")
    @Test
    void getTelephones() {
        final List<Telephone> telephoneList = telephoneRepository.getTelephones(0, 5);

        assertNotNull(telephoneList);

        assertEquals(3, telephoneList.size());
        assertEquals(100, telephoneList.get(0).getId());
        assertEquals(101, telephoneList.get(1).getId());
        assertEquals(102, telephoneList.get(2).getId());
        assertEquals("988080808", telephoneList.get(0).getNumber());
        assertEquals("988080809", telephoneList.get(1).getNumber());
        assertEquals("988080810", telephoneList.get(2).getNumber());
    }

    @Test
    void persistTelephone() {
        final Telephone telephone = new Telephone("55", "79", "991900010");

        telephoneRepository.persistTelephone(telephone);

        final Telephone persistedTelephone = entityManager
                .createQuery("from Telephone where id = :id", Telephone.class)
                .setParameter("id", 1)
                .getSingleResult();

        assertNotNull(persistedTelephone);

        assertEquals(1, persistedTelephone.getId());
        assertEquals("55", persistedTelephone.getCountryCode());
        assertEquals("79", persistedTelephone.getAreaCode());
        assertEquals("991900010", persistedTelephone.getNumber());
    }
}