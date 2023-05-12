package augusto108.ces.advhibernate.data;

import augusto108.ces.advhibernate.domain.entities.Telephone;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:sample-student.properties")
public class StudentTelephoneLoad {
    @Value("${student.telephone.country-code}")
    private String countryCode;

    @Value("${student.telephone.area-code}")
    private String areaCode;

    @Value("${student.telephone.number}")
    private String number;

    public Telephone createStudentTelephone() {
        return new Telephone(countryCode, areaCode, number);
    }
}
