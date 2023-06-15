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
        assertEquals("+55 (79) 988080808", t1.toString());
        assertEquals("+55 (79) 988080809", t2.toString());
        assertEquals("+55 (79) 988080810", t3.toString());
    }

    @Sql("/telephones-script.sql")
    @Test
    void getTelephones() {
        final List<Telephone> telephoneList = telephoneRepository.getTelephones(0, 10);

        assertNotNull(telephoneList);

        assertEquals(8, telephoneList.size());

        boolean contains988080808 = false;
        for (Telephone telephone : telephoneList) {
            if (telephone.toString().equals("+55 (79) 988080808")) {
                contains988080808 = true;
                break;
            }
        }

        boolean contains988080809 = false;
        for (Telephone telephone : telephoneList) {
            if (telephone.toString().equals("+55 (79) 988080809")) {
                contains988080809 = true;
                break;
            }
        }

        boolean contains988080810 = false;
        for (Telephone telephone : telephoneList) {
            if (telephone.toString().equals("+55 (79) 988080810")) {
                contains988080810 = true;
                break;
            }
        }

        assertTrue(contains988080808 && contains988080809 && contains988080810);
    }

    @Test
    void persistTelephone() {
        final Telephone telephone = new Telephone("55", "91", "965550022");

        telephoneRepository.persistTelephone(telephone);

        final List<Telephone> telephoneList = entityManager
                .createQuery("from Telephone order by id", Telephone.class)
                .getResultList();

        assertNotNull(telephoneList);

        boolean containsPhone = false;
        for (Telephone t : telephoneList) {
            if (t.toString().equals("+55 (91) 965550022")) {
                containsPhone = true;
                break;
            }
        }

        assertTrue(containsPhone);
    }
}