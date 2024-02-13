package augusto108.ces.advhibernate.data;

import augusto108.ces.advhibernate.domain.entities.Employee;
import augusto108.ces.advhibernate.domain.entities.Name;
import augusto108.ces.advhibernate.domain.entities.Telephone;
import augusto108.ces.advhibernate.domain.entities.enums.Role;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:sample-employee.properties")
public class EmployeeLoad {

    @Value("${employee.first-name}")
    private String firstName;

    @Value("${employee.middle-name}")
    private String middleName;

    @Value("${employee.last-name}")
    private String lastName;

    @Value("${employee.email}")
    private String email;

    @Value("${employee.role}")
    private Role role;

    @Value("${employee.telephone.country-code}")
    private String countryCode;

    @Value("${employee.telephone.area-code}")
    private String areaCode;

    @Value("${employee.telephone1.number}")
    private String firstPhoneNumber;

    @Value("${employee.telephone2.number}")
    private String secondPhoneNumber;

    public Employee createEmployee() {
        final Name name = new Name(firstName, middleName, lastName);
        return new Employee(name, name, email, role);
    }

    public Telephone[] createTelephones() {
        return new Telephone[]{
                new Telephone(countryCode, areaCode, firstPhoneNumber),
                new Telephone(countryCode, areaCode, secondPhoneNumber)
        };
    }
}
