package attnftasap.adt.service;
import attnftasap.adt.repository.SuggestionsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import attnftasap.adt.model.Suggestions;
import java.util.List;

@Service
public class SuggestionsServiceImpl implements SuggestionsService {

    @Autowired
    private SuggestionsRepository suggestionsRepository;

    @Override
    public List<Suggestions> displaySuggestion(String studentId) {
        return suggestionsRepository.findByChildUuid(studentId);
    }

    @Override
    public void deleteSuggestion(String id) {
        suggestionsRepository.deleteById(id);
    }

    @Override
    public List<Suggestions> displayCurrentSuggestions() {
        return suggestionsRepository.findAll();
    }

    @Override
    @Transactional
    public void saveSuggestion(Suggestions suggestion) {
        suggestionsRepository.save(suggestion);
    }

}
