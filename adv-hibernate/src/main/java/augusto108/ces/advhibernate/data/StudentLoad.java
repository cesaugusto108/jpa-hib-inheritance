package augusto108.ces.advhibernate.data;

import augusto108.ces.advhibernate.domain.entities.Name;
import augusto108.ces.advhibernate.domain.entities.Student;
import augusto108.ces.advhibernate.domain.entities.Telephone;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:sample-student.properties")
public class StudentLoad {

    @Value("${student.first-name}")
    private String firstName;

    @Value("${student.middle-name}")
    private String middleName;

    @Value("${student.last-name}")
    private String lastName;

    @Value("${student.email}")
    private String email;

    @Value("${student.telephone.country-code}")
    private String countryCode;

    @Value("${student.telephone.area-code}")
    private String areaCode;

    @Value("${student.telephone.number}")
    private String number;

    @Value("${student.registration}")
    private String registration;

    public Student createStudent() {
        final Name name = new Name(firstName, middleName, lastName);
        return new Student(name, name, email, registration);
    }

    public Telephone createStudentTelephone() {
        return new Telephone(countryCode, areaCode, number);
    }
}
