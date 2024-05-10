package attnftasap.adt.repository;

import attnftasap.adt.model.Student;
import attnftasap.adt.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
    Optional<User> findFirstByUsername(String username);
}
