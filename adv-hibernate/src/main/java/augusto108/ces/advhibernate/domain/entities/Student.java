package augusto108.ces.advhibernate.domain.entities;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue(value = "student")
@JsonPropertyOrder(value = {"id", "registration", "name", "socialName", "email", "telephones"})
public final class Student extends Person {

    @Column(name = "registration", length = 12)
    private String registration;

    public Student() {
    }

    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    public Student(Name name, Name socialName, String email, String registration) {
        super(name, socialName, email);
        this.registration = registration;
    }

    public String getRegistration() {
        return registration;
    }

    public void setRegistration(String registration) {
        this.registration = registration;
    }

    @Override
    public String toString() {
        return super.toString() + " (" + registration + ")";
    }
}
