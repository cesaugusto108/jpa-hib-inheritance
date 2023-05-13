package augusto108.ces.advhibernate.domain.entities;

import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name = "student")
public final class Student extends Person {
    @Column(name = "registration", length = 12)
    private String registration;

    public Student() {
    }

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

    @JoinTable(
            name = "student_telephone",
            joinColumns = @JoinColumn(name = "student_id"),
            inverseJoinColumns = @JoinColumn(name = "telephone_id")
    )
    @Override
    public Set<Telephone> getTelephones() {
        return super.getTelephones();
    }

    @Override
    public String toString() {
        return super.toString() + " (" + registration + ")";
    }
}
