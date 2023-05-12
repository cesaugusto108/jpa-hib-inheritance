package augusto108.ces.advhibernate.domain.entities;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE) // Inheritance single table strategy
@Table(name = "person")
@DiscriminatorColumn(name = "person_type", discriminatorType = DiscriminatorType.STRING)
public non-sealed class Person extends BaseEntity {
    @Embedded
    private Name name;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "firstName", column = @Column(name = "social_first_name")),
            @AttributeOverride(name = "middleName", column = @Column(name = "social_middle_name")),
            @AttributeOverride(name = "lastName", column = @Column(name = "social_last_name"))
    }
    )
    private Name socialName;

    @Column(name = "email", nullable = false, length = 60)
    private String email;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "person_telephone",
            joinColumns = @JoinColumn(name = "person_id"),
            inverseJoinColumns = @JoinColumn(name = "telephone_id")
    )
    private final Set<Telephone> telephones = new HashSet<>();

    public Person() {
    }

    public Person(Name name, Name socialName, String email) {
        this.name = name;
        this.socialName = socialName;
        this.email = email;
    }

    public Name getName() {
        return name;
    }

    public void setName(Name name) {
        this.name = name;
    }

    public Name getSocialName() {
        return socialName;
    }

    public void setSocialName(Name socialName) {
        this.socialName = socialName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Set<Telephone> getTelephones() {
        return telephones;
    }

    @Override
    public String toString() {
        return socialName.toString();
    }
}
