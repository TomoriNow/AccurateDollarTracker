package attnftasap.adt.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Entity
@Getter @Setter
public class GuardianshipRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "studentID", referencedColumnName = "userUUID")
    private Student student;

    @ManyToOne
    @JoinColumn(name = "guardianID", referencedColumnName = "userUUID")
    private Guardian guardian;


    public GuardianshipRequest() {}

    public GuardianshipRequest(Student student, Guardian guardian) {
        this.student = student;
        this.guardian = guardian;
    }
}