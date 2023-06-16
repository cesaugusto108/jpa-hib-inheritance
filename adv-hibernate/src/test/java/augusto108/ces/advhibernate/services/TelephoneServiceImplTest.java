package augusto108.ces.advhibernate.services;

import augusto108.ces.advhibernate.domain.entities.Telephone;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@Transactional
@ActiveProfiles("test")
@DisplayNameGeneration(DisplayNameGenerator.Simple.class)
class TelephoneServiceImplTest {
    @Autowired
    private TelephoneService telephoneService;

    @PersistenceContext
    private EntityManager entityManager;

    @AfterEach
    void tearDown() {
        entityManager.createNativeQuery("delete from telephone;");
    }

    @Test
    void persistTelephone() {
        final Telephone telephone = new Telephone("55", "85", "999159977");

        telephoneService.persistTelephone(telephone);

        final List<Telephone> telephones = entityManager
                .createQuery("from Telephone order by id", Telephone.class).getResultList();

        boolean contains999159977 = false;
        for (Telephone t : telephones) {
            if (t.toString().equals("+55 (85) 999159977")) {
                contains999159977 = true;
                break;
            }
        }

        assertTrue(contains999159977);
    }
}