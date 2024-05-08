package attnftasap.adt.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.UUID;

public class UserTest {
    private UUID userUUID;
    private String username;
    private String email;
    private String password;
    private User user;
    @BeforeEach
    public void setup() {
        this.userUUID = UUID.randomUUID();
        this.username = "username";
        this.email = "email";
        this.password = "password";
        this.user = new User();
    }

    @Test
    public void testConstructor() {
        User user1 = new User(username, email, password);

        assertEquals("username", user1.getUsername());
        assertEquals("email", user1.getEmail());
        assertEquals("password", user1.getPassword());
    }

    @Test
    public void testGetSetId() {
        user.setUserUUID(userUUID);

        assertEquals(userUUID, user.getUserUUID());
    }

    @Test
    public void testGetSetUsername() {
        user.setUsername(username);

        assertEquals("username", user.getUsername());
    }

    @Test
    public void testGetSetEmail() {
        user.setEmail(email);

        assertEquals("email", user.getEmail());
    }

    @Test
    public void testGetSetPassword() {
        user.setPassword(password);

        assertEquals("password", user.getPassword());
    }
}
