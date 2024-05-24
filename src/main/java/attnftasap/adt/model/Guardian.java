package attnftasap.adt.model;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.FetchType;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
public class Guardian extends User{
    @OneToMany(mappedBy = "guardian", fetch = FetchType.EAGER)
    private List<Student> students;

    public Guardian() {}

    public Guardian(String username, String email, String password) {
        super(username, email, password);
        this.students = new ArrayList<>();
    }

    public void addStudent(Student student) {
        if (this.students == null) {
            students = new ArrayList<>();
        }
        student.setGuardian(this);
        students.add(student);
    }

    public void removeStudent(Student student) {
        if (this.students == null) {
            students = new ArrayList<>();
        }
        student.setGuardian(null);
        students.remove(student);
    }
}
