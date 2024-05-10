package attnftasap.adt.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table(name = "categories")
@Getter @Setter
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID categoryUUID;

    @Column(name = "name")
    private String name;

    @ManyToOne
    @JoinColumn(name = "owned", referencedColumnName = "userUUID")
    private Student student;

    @Column(name = "description")
    private String description;

    public Category() {}

    public Category(Student student, String name, String description) {
        this.name = name;
        this.student = student;
        this.description = description;
    }

    @Override
    public String toString() {
        return name;
    }
}
