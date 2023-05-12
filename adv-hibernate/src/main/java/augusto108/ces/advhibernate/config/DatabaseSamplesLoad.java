package augusto108.ces.advhibernate.config;

import augusto108.ces.advhibernate.data.StudentLoad;
import augusto108.ces.advhibernate.data.StudentTelephoneLoad;
import augusto108.ces.advhibernate.domain.entities.Student;
import augusto108.ces.advhibernate.domain.entities.Telephone;
import augusto108.ces.advhibernate.services.PersonService;
import augusto108.ces.advhibernate.services.TelephoneService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DatabaseSamplesLoad {
    private final PersonService personService;
    private final TelephoneService telephoneService;
    private final StudentLoad studentLoad;
    private final StudentTelephoneLoad telephoneLoad;

    public DatabaseSamplesLoad(PersonService personService, TelephoneService telephoneService, StudentLoad studentLoad, StudentTelephoneLoad telephoneLoad) {
        this.personService = personService;
        this.telephoneService = telephoneService;
        this.studentLoad = studentLoad;
        this.telephoneLoad = telephoneLoad;
    }

    @Bean
    void persistStudent() {
        final Student student = studentLoad.createStudent();
        final Telephone telephone = telephoneLoad.createStudentTelephone();

        telephoneService.persistTelephone(telephone);

        student.getTelephones().add(telephone);

        personService.persistPerson(student);
    }
}
