package augusto108.ces.advhibernate.domain.entities;

import jakarta.persistence.*;

import java.util.Objects;

@MappedSuperclass
public abstract sealed class BaseEntity permits Person, Telephone {
    @Id
    @SequenceGenerator(name = "seq_gen", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "seq_gen")
    @Column(name = "id", nullable = false, unique = true)
    private Integer id;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BaseEntity that = (BaseEntity) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
