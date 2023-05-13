package augusto108.ces.advhibernate.domain.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;

@Entity
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

    @Override
    public String toString() {
        return super.toString() + " (" + registration + ")";
    }
}
