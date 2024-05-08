package attnftasap.adt.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class GuardianTest {
    private UUID userUUID;
    private String username;
    private String email;
    private String password;
    private Guardian guardian;
    @BeforeEach
    public void setup() {
        this.userUUID = UUID.randomUUID();
        this.username = "username";
        this.email = "email";
        this.password = "password";
        this.guardian = new Guardian();
    }

    @Test
    public void testConstructor() {
        Guardian guardian1 = new Guardian(username, email, password);

        assertEquals("username", guardian1.getUsername());
        assertEquals("email", guardian1.getEmail());
        assertEquals("password", guardian1.getPassword());
        assertTrue(guardian1.getStudents().isEmpty());
    }

    @Test
    public void testGetSetId() {
        guardian.setUserUUID(userUUID);

        assertEquals(userUUID, guardian.getUserUUID());
    }

    @Test
    public void testGetSetUsername() {
        guardian.setUsername(username);

        assertEquals("username", guardian.getUsername());
    }

    @Test
    public void testGetSetEmail() {
        guardian.setEmail(email);

        assertEquals("email", guardian.getEmail());
    }

    @Test
    public void testGetSetPassword() {
        guardian.setPassword(password);

        assertEquals("password", guardian.getPassword());
    }

    @Test
    public void testGetSetStudents() {
        List<Student> students = new ArrayList<>();
        Student student1 = new Student(username, email, password);
        Student student2 = new Student("username2", "email2", "password2");

        students.add(student1);
        students.add(student2);

        guardian.setStudents(students);
        List<Student> result = guardian.getStudents();

        assertEquals(2, result.size());
        assertEquals(student1, result.getFirst());
        assertEquals("username", result.getFirst().getUsername());
        assertEquals("email", result.getFirst().getEmail());
        assertEquals("password", result.getFirst().getPassword());
        assertEquals(student2, result.getLast());
        assertEquals("username2", result.getLast().getUsername());
        assertEquals("email2", result.getLast().getEmail());
        assertEquals("password2", result.getLast().getPassword());
    }

    @Test
    public void testAddStudents() {
        guardian.addStudent(new Student("username", "email", "password"));
        guardian.addStudent(new Student("username2", "email2", "password2"));

        assertEquals("username", guardian.getStudents().getFirst().getUsername());
        assertEquals("username2", guardian.getStudents().getLast().getUsername());
        assertEquals("email", guardian.getStudents().getFirst().getEmail());
        assertEquals("email2", guardian.getStudents().getLast().getEmail());
        assertEquals("password", guardian.getStudents().getFirst().getPassword());
        assertEquals("password2", guardian.getStudents().getLast().getPassword());
        assertEquals(guardian, guardian.getStudents().getFirst().getGuardian());
        assertEquals(guardian, guardian.getStudents().getLast().getGuardian());
    }

    @Test
    public void testRemoveStudentThatExists() {
        Student student = new Student("username", "email", "password");
        Student student1 = new Student("username", "email", "password");
        guardian.addStudent(student);
        guardian.addStudent(student1);

        guardian.removeStudent(student);

        assertEquals(1, guardian.getStudents().size());
        assertEquals(student1, guardian.getStudents().getFirst());
        assertFalse(guardian.getStudents().contains(student));
    }

    @Test
    public void testRemoveStudentThatDoesNotExist() {
        Student student = new Student("username", "email", "password");
        Student student1 = new Student("username", "email", "password");
        guardian.addStudent(student1);

        guardian.removeStudent(student);

        assertEquals(1, guardian.getStudents().size());
        assertEquals(student1, guardian.getStudents().getFirst());
        assertFalse(guardian.getStudents().contains(student));
    }

    @Test
    public void testRemoveStudentEmpty() {
        Student student = new Student("username", "email", "password");

        guardian.removeStudent(student);

        assertEquals(0, guardian.getStudents().size());
        assertFalse(guardian.getStudents().contains(student));
    }
}
