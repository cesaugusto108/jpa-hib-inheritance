package augusto108.ces.advhibernate.services;

import augusto108.ces.advhibernate.domain.entities.Telephone;
import jakarta.persistence.NoResultException;
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

    @BeforeEach
    void setUp() {
        jdbcTemplate.execute(
                "insert into telephone (`id`, `area_code`, `country_code`, `number`) " +
                        "values (100, '79', '55', '999990099');");
    }

    @AfterEach
    void tearDown() {
        jdbcTemplate.execute("delete from telephone");
    }

    @Test
    void persistTelephone() {
        telephoneService.persistTelephone(new Telephone("55", "79", "999990000"));

        final Telephone telephone = telephoneService.getTelephoneById(2);

        assertNotNull(telephone);
        assertEquals(2, telephone.getId());
        assertEquals("55", telephone.getCountryCode());
        assertEquals("79", telephone.getAreaCode());
        assertEquals("999990000", telephone.getNumber());
        assertEquals(2, telephoneService.getTelephones(0, 5).size());
    }

    @Test
    void getTelephoneById() {
        final Telephone telephone = telephoneService.getTelephoneById(100);

        assertNotNull(telephone);
        assertEquals(100, telephone.getId());
        assertEquals("55", telephone.getCountryCode());
        assertEquals("79", telephone.getAreaCode());
        assertEquals("999990099", telephone.getNumber());

        assertThrows(NoResultException.class, () -> telephoneService.getTelephoneById(0));
    }

    @Test
    void getTelephones() {
        telephoneService.persistTelephone(new Telephone("55", "71", "999980101"));

        final List<Telephone> telephoneList = telephoneService.getTelephones(0, 5);

        assertNotNull(telephoneList);
        assertEquals(2, telephoneList.size());
        assertEquals(1, telephoneList.get(0).getId());
        assertEquals(100, telephoneList.get(1).getId());
        assertEquals("999980101", telephoneList.get(0).getNumber());
        assertEquals("999990099", telephoneList.get(1).getNumber());
    }
}