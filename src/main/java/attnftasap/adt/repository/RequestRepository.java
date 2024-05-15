package attnftasap.adt.repository;

import attnftasap.adt.model.Guardian;
import attnftasap.adt.model.GuardianshipRequest;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface RequestRepository extends JpaRepository<GuardianshipRequest, UUID> {

    @Transactional
    @Query(value = "SELECT r FROM GuardianshipRequest r WHERE r.student.userUUID = ?1")
    List<GuardianshipRequest> findAllByStudentID(UUID studentId);

    @Transactional
    @Query("SELECT r.guardian FROM GuardianshipRequest r WHERE r.id IN :requestIds")
    List<Guardian> findGuardiansByRequestIds(@Param("requestIds") List<UUID> requestIds);

    @Transactional
    @Query("SELECT r.guardian FROM GuardianshipRequest r WHERE r.student.userUUID = ?1")
    Guardian findGuardianByStudentId(UUID studentId);

    @Modifying
    @Transactional
    @Query("UPDATE GuardianshipRequest r SET r.guardian = :guardian WHERE r.student.userUUID = :studentId")
    void setIsGuardianByID(@Param("studentId") UUID studentId, @Param("guardian") Guardian guardian);

    @Modifying
    @Transactional
    @Query("DELETE FROM GuardianshipRequest r WHERE r.student.userUUID = ?1")
    void removeGuardianByStudentID(UUID studentId);
}