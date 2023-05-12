package augusto108.ces.advhibernate.data;

import augusto108.ces.advhibernate.domain.entities.Name;
import augusto108.ces.advhibernate.domain.entities.Student;
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

    @Value("${student.registration}")
    private String registration;

    public Student createStudent() {
        final Name name = new Name(firstName, middleName, lastName);

        return new Student(name, name, email, registration);
    }
}
