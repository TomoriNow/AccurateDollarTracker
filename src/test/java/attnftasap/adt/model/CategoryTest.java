package attnftasap.adt.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class CategoryTest {
    private String name;
    private String description;
    private Student owner;
    private Category category;

    @BeforeEach
    public void setUp() {
        this.name = "name";
        this.description = "description";
        this.owner = new Student("username", "email", "password");
        this.category = new Category();
    }

    @Test
    public void testConstructor() {
        Category category1 = new Category(owner, name, description);

        assertEquals("name", category1.getName());
        assertEquals("description", category1.getDescription());
        assertEquals(owner, category1.getStudent());
        assertEquals("username", category1.getStudent().getUsername());
        assertEquals("email", category1.getStudent().getEmail());
        assertEquals("password", category1.getStudent().getPassword());
    }

    @Test
    public void testGetSetName() {
        category.setName(name);

        assertEquals("name", category.getName());
    }

    @Test
    public void testGetSetDescription() {
        category.setDescription(description);

        assertEquals("description", category.getDescription());
    }

    @Test
    public void testGetSetStudent() {
        category.setStudent(owner);

        assertEquals(owner, category.getStudent());
        assertEquals("username", category.getStudent().getUsername());
        assertEquals("email", category.getStudent().getEmail());
        assertEquals("password", category.getStudent().getPassword());
    }
}
