package attnftasap.adt.repository;
import attnftasap.adt.model.Suggestions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SuggestionsRepository extends JpaRepository<Suggestions, String> {
    List<Suggestions> findByChildUuid(String childUuid);
}
