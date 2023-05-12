package augusto108.ces.advhibernate.services;

import augusto108.ces.advhibernate.domain.entities.Telephone;
import augusto108.ces.advhibernate.repositories.TelephoneRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class TelephoneServiceImpl implements TelephoneService {
    private final TelephoneRepository telephoneRepository;

    public TelephoneServiceImpl(TelephoneRepository telephoneRepository) {
        this.telephoneRepository = telephoneRepository;
    }

    @Override
    public void persistTelephone(Telephone telephone) {
        telephoneRepository.persistTelephone(telephone);
    }
}
