package augusto108.ces.advhibernate.data;

import augusto108.ces.advhibernate.domain.entities.Instructor;
import augusto108.ces.advhibernate.domain.entities.Name;
import augusto108.ces.advhibernate.domain.entities.Telephone;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:sample-instructor.properties")
public class InstructorLoad {
    @Value("${instructor.first-name}")
    private String firstName;

    @Value("${instructor.middle-name}")
    private String middleName;

    @Value("${instructor.last-name}")
    private String lastName;

    @Value("${instructor.email}")
    private String email;

    @Value("${instructor.specialty}")
    private String specialty;

    @Value("${instructor.telephone.country-code}")
    private String countryCode;

    @Value("${instructor.telephone.area-code}")
    private String areaCode;

    @Value("${instructor.telephone1.number}")
    private String firstPhoneNumber;

    @Value("${instructor.telephone2.number}")
    private String secondPhoneNumber;


    public Instructor createInstructor() {
        final Name name = new Name(firstName, middleName, lastName);

        return new Instructor(name, name, email, specialty);
    }

    public Telephone[] createTelephones() {
        return new Telephone[]{
                new Telephone(countryCode, areaCode, firstPhoneNumber),
                new Telephone(countryCode, areaCode, secondPhoneNumber)
        };
    }
}
