package attnftasap.adt.repository;

import attnftasap.adt.model.Guardian;
import attnftasap.adt.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface GuardianRepository extends JpaRepository<Guardian, UUID> {
    Guardian findByUsername(String username);
    Optional<Guardian> findByUsernameAndPassword(String username, String password);
}
