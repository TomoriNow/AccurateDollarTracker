package attnftasap.adt.repository;
import attnftasap.adt.model.User;
import attnftasap.adt.model.Guardian;
import attnftasap.adt.model.GuardianshipRequest;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface RequestRepository extends JpaRepository<GuardianshipRequest, UUID> {
    Optional<GuardianshipRequest> findById(UUID requestId);

    @Transactional
    @Query(value = "SELECT r FROM GuardianshipRequest r WHERE r.student.userUUID = ?1")
    List<GuardianshipRequest> findAllByStudentID(UUID studentId);

    @Transactional
    @Query("SELECT r.guardian FROM GuardianshipRequest r WHERE r.student.userUUID = ?1")
    Guardian findGuardianByStudentId(UUID studentId);

    @Modifying
    @Transactional
    @Query("UPDATE Student s SET s.guardian = :guardian WHERE s.userUUID = :studentId")
    void setIsGuardianByID(@Param("studentId") UUID studentId, @Param("guardian") Guardian guardian);

    @Modifying
    @Transactional
    @Query("UPDATE Student s SET s.guardian=null WHERE s.userUUID = :studentId")
    void setGuardianNull(@Param("studentId") UUID studentId);

    @Modifying
    @Transactional
    @Query("DELETE FROM GuardianshipRequest r WHERE r.student.userUUID = :studentId AND r.id = :requestId")
    void removeGuardianByStudentIdAndRequestId(@Param("studentId") UUID studentId, @Param("requestId") UUID requestId);
}