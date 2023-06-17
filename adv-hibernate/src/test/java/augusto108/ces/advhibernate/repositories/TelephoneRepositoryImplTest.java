package augusto108.ces.advhibernate.repositories;

import augusto108.ces.advhibernate.domain.entities.Telephone;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@ActiveProfiles("test")
@DisplayNameGeneration(DisplayNameGenerator.IndicativeSentences.class)
class TelephoneRepositoryImplTest {
    @Autowired
    private TelephoneRepository telephoneRepository;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @PersistenceContext
    private EntityManager entityManager;

    @BeforeEach
    void setUp() {
        jdbcTemplate.execute(
                "insert into telephone (`id`, `area_code`, `country_code`, `number`) " +
                        "values (10000, '71', '55', '988909809');");
    }

    @AfterEach
    void tearDown() {
        jdbcTemplate.execute("delete from telephone;");
    }

    @Test
    void persistTelephone() {
        telephoneRepository.persistTelephone(new Telephone("55", "79", "988805555"));

        final List<Telephone> telephones = entityManager
                .createQuery("from Telephone order by id", Telephone.class)
                .getResultList();

        assertEquals(2, telephones.size());
        assertEquals("+55 (79) 988805555", telephones.get(0).toString());
        assertEquals("+55 (71) 988909809", telephones.get(1).toString());
    }
}