package attnftasap.adt.service;

import attnftasap.adt.model.Guardian;
import attnftasap.adt.model.GuardianshipRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public interface RequestService {
    List<GuardianshipRequest> getGuardianRequestsByID(UUID studentId);
    Guardian getIsGuardianByID(UUID studentId);
    void removeGuardianByID(UUID studentId, UUID requestId, boolean accept);
}