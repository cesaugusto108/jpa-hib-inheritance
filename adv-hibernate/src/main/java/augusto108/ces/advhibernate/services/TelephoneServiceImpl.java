package augusto108.ces.advhibernate.services;

import augusto108.ces.advhibernate.domain.entities.Telephone;
import augusto108.ces.advhibernate.repositories.TelephoneRepository;
import jakarta.persistence.NoResultException;
import jakarta.transaction.Transactional;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class TelephoneServiceImpl implements TelephoneService {
    private final TelephoneRepository telephoneRepository;

    public TelephoneServiceImpl(TelephoneRepository telephoneRepository) {
        this.telephoneRepository = telephoneRepository;
    }

    @Override
    public Telephone getTelephoneById(Integer id) {
        try {
            return telephoneRepository.getTelephoneById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new NoResultException(e.getMessage());
        }
    }

    @Override
    public List<Telephone> getTelephones(int page, int max) {
        return telephoneRepository.getTelephones(page, max);
    }

    @Override
    public void persistTelephone(Telephone telephone) {
        telephoneRepository.persistTelephone(telephone);
    }
}
