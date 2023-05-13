package augusto108.ces.advhibernate.domain.entities;

import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name = "instructor")
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

    @JoinTable(
            name = "instructor_telephone",
            joinColumns = @JoinColumn(name = "instructor_id"),
            inverseJoinColumns = @JoinColumn(name = "telephone_id")
    )
    @Override
    public Set<Telephone> getTelephones() {
        return super.getTelephones();
    }

    @Override
    public String toString() {
        return super.toString() + " (" + specialty + ")";
    }
}
