package augusto108.ces.advhibernate.services;

import augusto108.ces.advhibernate.domain.entities.Telephone;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@DisplayNameGeneration(DisplayNameGenerator.Simple.class)
class TelephoneServiceImplTest {
    @Autowired
    private TelephoneService telephoneService;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @PersistenceContext
    private EntityManager entityManager;

    @BeforeEach
    void setUp() {
        jdbcTemplate.execute(
                "insert into telephone (`id`, `area_code`, `country_code`, `number`) " +
                        "values (100, '79', '55', '9899900399');");
    }

    @AfterEach
    void tearDown() {
        jdbcTemplate.execute("delete from person_telephone");
        jdbcTemplate.execute("delete from telephone");
    }

    @Test
    void persistTelephone() {
        telephoneService.persistTelephone(new Telephone("55", "85", "939990070"));

        List<Telephone> telephoneList = entityManager
                .createQuery("from Telephone order by id", Telephone.class)
                .getResultList();

        boolean containsNumber = false;
        for (Telephone telephone : telephoneList) {
            if (telephone.toString().equals("+55 (85) 939990070")) {
                containsNumber = true;
                break;
            }
        }

        assertTrue(containsNumber);
    }

    @Test
    void getTelephoneById() {
        final Telephone telephone = telephoneService.getTelephoneById(100);

        assertNotNull(telephone);
        assertEquals(100, telephone.getId());
        assertEquals("55", telephone.getCountryCode());
        assertEquals("79", telephone.getAreaCode());
        assertEquals("9899900399", telephone.getNumber());

        assertThrows(NoResultException.class, () -> telephoneService.getTelephoneById(0));
    }

    @Test
    void getTelephones() {
        telephoneService.persistTelephone(new Telephone("55", "71", "999980101"));

        final List<Telephone> telephoneList = telephoneService.getTelephones(0, 5);

        assertNotNull(telephoneList);
    }
}