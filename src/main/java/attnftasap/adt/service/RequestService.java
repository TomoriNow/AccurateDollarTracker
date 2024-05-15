package attnftasap.adt.service;

import attnftasap.adt.model.Guardian;
import attnftasap.adt.model.GuardianshipRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public interface RequestService {
    List<GuardianshipRequest> getGuardianRequestsByID(UUID studentId);
    List<Guardian> findGuardiansByRequestIds(List<UUID> guardianshipRequestIds);
    Guardian getIsGuardianByID(UUID studentId);
    void removeGuardianByID(UUID studentId, boolean accept);
}