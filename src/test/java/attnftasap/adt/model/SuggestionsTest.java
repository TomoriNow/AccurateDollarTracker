import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class SuggestionsTest {

    @Test
    public void testGettersAndSetters() {
        // Create a suggestions object
        Suggestions suggestions = new Suggestions();

        // Set values using setters
        suggestions.setUuid("123");
        suggestions.setChildUuid("child123");
        suggestions.setGuardianUuid("guardian123");
        suggestions.setSuggestion("Test suggestion");

        // Test getters
        assertEquals("123", suggestions.getUuid());
        assertEquals("child123", suggestions.getChildUuid());
        assertEquals("guardian123", suggestions.getGuardianUuid());
        assertEquals("Test suggestion", suggestions.getSuggestion());
    }

    @Test
    public void testConstructor() {
        // Create a suggestions object using constructor
        Suggestions suggestions = new Suggestions("123", "child123", "guardian123", "Test suggestion");

        // Test getters
        assertEquals("123", suggestions.getUuid());
        assertEquals("child123", suggestions.getChildUuid());
        assertEquals("guardian123", suggestions.getGuardianUuid());
        assertEquals("Test suggestion", suggestions.getSuggestion());
    }
}
