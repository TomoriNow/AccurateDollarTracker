package attnftasap.adt.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class SuggestionsServiceTest {

    @Mock
    private SuggestionsRepository suggestionsRepository;

    @InjectMocks
    private SuggestionsServiceImpl suggestionsService;

    @Test
    public void testDisplaySuggestion() {
        Suggestions suggestion1 = new Suggestions("1", "child1", "guardian1", "Suggestion 1");
        Suggestions suggestion2 = new Suggestions("2", "child1", "guardian2", "Suggestion 2");

        when(suggestionsRepository.findByChildUuid("child1")).thenReturn(Arrays.asList(suggestion1, suggestion2));

        List<Suggestions> suggestions = suggestionsService.displaySuggestion("child1");

        assertEquals(2, suggestions.size());
        assertEquals("1", suggestions.get(0).getUuid());
        assertEquals("child1", suggestions.get(0).getChildUuid());
        assertEquals("guardian1", suggestions.get(0).getGuardianUuid());
        assertEquals("Suggestion 1", suggestions.get(0).getSuggestion());

        assertEquals("2", suggestions.get(1).getUuid());
        assertEquals("child1", suggestions.get(1).getChildUuid());
        assertEquals("guardian2", suggestions.get(1).getGuardianUuid());
        assertEquals("Suggestion 2", suggestions.get(1).getSuggestion());
    }

    @Test
    public void testDeleteSuggestion() {

        suggestionsService.deleteSuggestion("1");

        verify(suggestionsRepository, times(1)).deleteById("1");
    }
}
