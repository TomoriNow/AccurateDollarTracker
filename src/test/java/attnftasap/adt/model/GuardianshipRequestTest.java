package attnftasap.adt.model;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class GuardianshipRequestTest {

    private GuardianshipRequest guardianshipRequest;
    private Student student;
    private Guardian guardian;

    @BeforeEach
    void setUp() {
        student = new Student();
        student.setUserUUID(UUID.randomUUID());
        student.setUsername("student1");
        student.setEmail("student1@example.com");
        student.setPassword("password1");

        guardian = new Guardian();
        guardian.setUserUUID(UUID.randomUUID());
        guardian.setUsername("guardian1");
        guardian.setEmail("guardian1@example.com");
        guardian.setPassword("password1");

        guardianshipRequest = new GuardianshipRequest(student, guardian);
    }

    @AfterEach
    void tearDown() {
        guardianshipRequest = null;
        student = null;
        guardian = null;
    }

    @Test
    void getId() {
        assertNull(guardianshipRequest.getId());
    }

    @Test
    void getStudent() {
        assertEquals(student, guardianshipRequest.getStudent());
    }

    @Test
    void getGuardian() {
        assertEquals(guardian, guardianshipRequest.getGuardian());
    }

    @Test
    void setId() {
        UUID newId = UUID.randomUUID();
        guardianshipRequest.setId(newId);
        assertEquals(newId, guardianshipRequest.getId());
    }

    @Test
    void setStudent() {
        Student newStudent = new Student();
        newStudent.setUserUUID(UUID.randomUUID());
        newStudent.setUsername("student2");
        newStudent.setEmail("student2@example.com");
        newStudent.setPassword("password2");

        guardianshipRequest.setStudent(newStudent);

        assertEquals(newStudent, guardianshipRequest.getStudent());
    }

    @Test
    void setGuardian() {
        Guardian newGuardian = new Guardian();
        newGuardian.setUserUUID(UUID.randomUUID());
        newGuardian.setUsername("guardian2");
        newGuardian.setEmail("guardian2@example.com");
        newGuardian.setPassword("password2");

        guardianshipRequest.setGuardian(newGuardian);

        assertEquals(newGuardian, guardianshipRequest.getGuardian());
    }
}