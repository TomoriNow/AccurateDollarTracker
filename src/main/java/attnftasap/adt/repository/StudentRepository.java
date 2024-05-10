package attnftasap.adt.repository;

import attnftasap.adt.model.Student;
import attnftasap.adt.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface StudentRepository extends JpaRepository<Student, UUID> {
    Student findByUsername(String username);
    Optional<Student> findByUsernameAndPassword(String username, String password);
}
