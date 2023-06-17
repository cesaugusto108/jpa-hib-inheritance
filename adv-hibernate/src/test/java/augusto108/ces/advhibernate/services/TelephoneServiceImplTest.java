package augusto108.ces.advhibernate.services;

import augusto108.ces.advhibernate.domain.entities.Telephone;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@ActiveProfiles("test")
@DisplayNameGeneration(DisplayNameGenerator.Simple.class)
class TelephoneServiceImplTest {
    @Autowired
    private TelephoneService telephoneService;

    @PersistenceContext
    private EntityManager entityManager;

    @Test
    void persistTelephone() {
        final Telephone telephone = new Telephone("55", "91", "991980005");

        telephoneService.persistTelephone(telephone);

        final List<Telephone> telephones = entityManager
                .createQuery("from Telephone order by id", Telephone.class)
                .getResultList();

        assertEquals(1, telephones.size());
        assertEquals("+55 (91) 991980005", telephones.get(0).toString());
    }
}