package attnftasap.adt.service;
import attnftasap.adt.model.Suggestions;
import java.util.List;

public interface SuggestionsService {

    List<Suggestions> displaySuggestion(String childUuid);

    void deleteSuggestion(String id);

    List<Suggestions> displayCurrentSuggestions();

    void saveSuggestion(Suggestions suggestion);
}
