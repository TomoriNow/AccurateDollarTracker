package attnftasap.adt.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "students")
@Getter @Setter
public class Student extends User {
    @ManyToOne
    @JoinColumn(name = "guardianId", referencedColumnName = "userUUID")
    private Guardian guardian;

    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL)
    private List<Category> categories;

    public Student() {}

    public Student(String username, String email, String password) {
        super(username, email, password);
        categories = new ArrayList<>();
    }

    public void addCategory(Category category) {
        if (this.categories == null) {
            categories = new ArrayList<>();
        }
        category.setStudent(this);
        categories.add(category);
    }

    public void addCategory(String name, String description) {
        if (this.categories == null) {
            categories = new ArrayList<>();
        }
        Category category = new Category(this, name, description);
        categories.add(category);
    }

    public void removeCategory(Category category) {
        if (this.categories == null) {
            categories = new ArrayList<>();
        }
        category.setStudent(null);
        categories.remove(category);
    }
}
