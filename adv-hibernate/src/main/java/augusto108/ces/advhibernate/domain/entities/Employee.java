package augusto108.ces.advhibernate.domain.entities;

import augusto108.ces.advhibernate.domain.entities.enums.Role;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import jakarta.persistence.*;

@Entity
@DiscriminatorValue(value = "employee")
@JsonPropertyOrder(value = {"id", "role", "name", "socialName", "email", "telephones"})
public final class Employee extends Person {

    @Enumerated(EnumType.STRING)
    @Column(name = "employee_role", length = 20)
    private Role role;

    public Employee() {
    }

    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
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

    @Override
    public String toString() {
        return super.toString() + " (" + role.toString() + ")";
    }
}
