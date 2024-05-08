package attnftasap.adt.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class StudentTest {
    private UUID userUUID;
    private String username;
    private String email;
    private String password;
    private Student student;
    @BeforeEach
    public void setup() {
        this.userUUID = UUID.randomUUID();
        this.username = "username";
        this.email = "email";
        this.password = "password";
        this.student = new Student();
    }

    @Test
    public void testConstructor() {
        Student student1 = new Student(username, email, password);

        assertEquals("username", student1.getUsername());
        assertEquals("email", student1.getEmail());
        assertEquals("password", student1.getPassword());
    }

    @Test
    public void testGetSetId() {
        student.setUserUUID(userUUID);

        assertEquals(userUUID, student.getUserUUID());
    }

    @Test
    public void testGetSetUsername() {
        student.setUsername(username);

        assertEquals("username", student.getUsername());
    }

    @Test
    public void testGetSetEmail() {
        student.setEmail(email);

        assertEquals("email", student.getEmail());
    }

    @Test
    public void testGetSetPassword() {
        student.setPassword(password);

        assertEquals("password", student.getPassword());
    }

    @Test
    public void testGetSetGuardian() {
        Guardian guardian = new Guardian("username2", "email2", "password2");
        student.setGuardian(guardian);

        assertEquals(guardian, student.getGuardian());
        assertEquals("username2", student.getGuardian().getUsername());
        assertEquals("email2", student.getGuardian().getEmail());
        assertEquals("password2", student.getGuardian().getPassword());
    }

    @Test
    public void testGetSetCategories() {
        Category category = new Category(student, "name1", "description1");
        Category category1 = new Category(student, "name2", "description2");

        List<Category> categories = new ArrayList<>();
        categories.add(category);
        categories.add(category1);

        student.setCategories(categories);

        assertEquals(categories, student.getCategories());
        assertEquals(category, student.getCategories().getFirst());
        assertEquals(category1, student.getCategories().getLast());
        assertEquals("name1", student.getCategories().getFirst().getName());
        assertEquals("name2", student.getCategories().getLast().getName());
        assertEquals("description1", student.getCategories().getFirst().getDescription());
        assertEquals("description2", student.getCategories().getLast().getDescription());
        assertEquals(student, student.getCategories().getFirst().getStudent());
        assertEquals(student, student.getCategories().getLast().getStudent());
    }

    @Test
    public void testAddCategories() {
        Student student1 = new Student();
        student.addCategory(new Category(student1, "name", "description"));
        student.addCategory(new Category(student1, "name2", "description2"));

        assertEquals("name", student.getCategories().getFirst().getName());
        assertEquals("name2", student.getCategories().getLast().getName());
        assertEquals("description", student.getCategories().getFirst().getDescription());
        assertEquals("description2", student.getCategories().getLast().getDescription());
        assertEquals(student, student.getCategories().getFirst().getStudent());
        assertEquals(student, student.getCategories().getLast().getStudent());
    }

    @Test
    public void testRemoveCategoryThatExists() {
        Student student1 = new Student();
        Category category = new Category(student1, "name", "description");
        Category category1 = new Category(student1, "name2", "description2");
        student.addCategory(category);
        student.addCategory(category1);

        student.removeCategory(category);

        assertEquals(1, student.getCategories().size());
        assertEquals(category1, student.getCategories().getFirst());
        assertFalse(student.getCategories().contains(category));
    }

    @Test
    public void testRemoveCategoryThatDoesNotExist() {
        Student student1 = new Student();
        Category category = new Category(student1, "name", "description");
        Category category1 = new Category(student1, "name2", "description2");
        student.addCategory(category1);

        student.removeCategory(category);

        assertEquals(1, student.getCategories().size());
        assertEquals(category1, student.getCategories().getFirst());
        assertFalse(student.getCategories().contains(category));
    }

    @Test
    public void testRemoveCategoryEmpty() {
        Category category = new Category(student, "name", "description");

        student.removeCategory(category);

        assertEquals(0, student.getCategories().size());
        assertFalse(student.getCategories().contains(category));
    }
}
