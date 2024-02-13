package augusto108.ces.advhibernate.domain.entities;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue(value = "instructor")
@JsonPropertyOrder(value = {"id", "specialty", "name", "socialName", "email", "telephones"})
public final class Instructor extends Person {

    @Column(name = "specialty", length = 20)
    private String specialty;

    public Instructor() {
    }

    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    public Instructor(Name name, Name socialName, String email, String specialty) {
        super(name, socialName, email);
        this.specialty = specialty;
    }

    public String getSpecialty() {
        return specialty;
    }

    public void setSpecialty(String specialty) {
        this.specialty = specialty;
    }

    @Override
    public String toString() {
        return super.toString() + " (" + specialty + ")";
    }
}
