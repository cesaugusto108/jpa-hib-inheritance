package augusto108.ces.advhibernate.repositories;

import augusto108.ces.advhibernate.domain.entities.Telephone;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@ActiveProfiles("test")
@DisplayNameGeneration(DisplayNameGenerator.Simple.class)
class TelephoneRepositoryImplTest {
    @Autowired
    private TelephoneRepository telephoneRepository;

    @PersistenceContext
    private EntityManager entityManager;

    @AfterEach
    void tearDown() {
        entityManager.createNativeQuery("delete from telephone;");
    }

    @Test
    void persistTelephone() {
        final Telephone telephone = new Telephone("55", "82", "999151515");

        telephoneRepository.persistTelephone(telephone);

        final List<Telephone> telephones = entityManager
                .createQuery("from Telephone order by id", Telephone.class).getResultList();

        boolean contains999151515 = false;
        for (Telephone t : telephones) {
            if (t.toString().equals("+55 (82) 999151515")) {
                contains999151515 = true;
                break;
            }
        }

        assertTrue(contains999151515);
    }
}