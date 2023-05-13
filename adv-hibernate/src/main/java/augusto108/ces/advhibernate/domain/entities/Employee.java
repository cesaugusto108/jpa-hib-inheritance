package augusto108.ces.advhibernate.domain.entities;

import augusto108.ces.advhibernate.domain.entities.enums.Role;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

@Entity
public final class Employee extends Person {
    @Enumerated(EnumType.STRING)
    @Column(name = "employee_role", length = 20)
    private Role role;

    public Employee() {
    }

    public Employee(Name name, Name socialName, String email, Role role) {
        super(name, socialName, email);
        this.role = role;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
