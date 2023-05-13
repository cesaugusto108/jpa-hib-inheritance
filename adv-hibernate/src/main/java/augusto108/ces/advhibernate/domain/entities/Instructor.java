package augusto108.ces.advhibernate.domain.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;

@Entity
public final class Instructor extends Person {
    @Column(name = "specialty", length = 20)
    private String specialty;

    public Instructor() {
    }

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
