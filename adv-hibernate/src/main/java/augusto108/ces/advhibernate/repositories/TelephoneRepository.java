package augusto108.ces.advhibernate.repositories;

import augusto108.ces.advhibernate.domain.entities.Telephone;

import java.util.List;

public interface TelephoneRepository {

    Telephone getTelephoneById(Integer id);

    List<Telephone> getTelephones(int page, int max);

    void persistTelephone(Telephone telephone);
}
