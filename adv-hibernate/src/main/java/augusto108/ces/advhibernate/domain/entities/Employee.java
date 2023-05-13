package augusto108.ces.advhibernate.domain.entities;

import augusto108.ces.advhibernate.domain.entities.enums.Role;
import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name = "employee")
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

    @JoinTable(
            name = "employee_telephone",
            joinColumns = @JoinColumn(name = "employee_id"),
            inverseJoinColumns = @JoinColumn(name = "telephone_id")
    )
    @Override
    public Set<Telephone> getTelephones() {
        return super.getTelephones();
    }

    @Override
    public String toString() {
        return super.toString() + " (" + role.toString() + ")";
    }
}
