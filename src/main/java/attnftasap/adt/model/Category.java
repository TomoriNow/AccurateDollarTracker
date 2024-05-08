package attnftasap.adt.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table(name = "categories")
@Getter @Setter
public class Category {
    @EmbeddedId
    private CategoryKey id;

    @ManyToOne
    @MapsId("studentId")
    @JoinColumn(name = "owned", referencedColumnName = "userUUID")
    private Student student;

    @Column(name = "description")
    private String description;

    public Category() {}

    public Category(Student student, String name, String description) {
        this.id = new CategoryKey(student.getUserUUID(), name);
        this.student = student;
        this.description = description;
    }

    public void setName(String name) {
        if (id == null) {
            this.id = new CategoryKey();
        }

        this.id.setName(name);
    }

    public String getName() {
        if (id == null) {
            return null;
        } else {
            return id.getName();
        }
    }
}
