package attnftasap.adt.service;

import attnftasap.adt.model.Guardian;
import attnftasap.adt.model.GuardianshipRequest;
import attnftasap.adt.repository.RequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class RequestServiceImpl implements RequestService {

    @Autowired
    private RequestRepository requestRepository;

    @Override
    public List<GuardianshipRequest> getGuardianRequestsByID(UUID studentId) {
        return requestRepository.findAllByStudentID(studentId);
    }


    @Override
    public Guardian getIsGuardianByID(UUID studentId) {
        return requestRepository.findGuardianByStudentId(studentId);
    }

    @Override
    @Transactional
    public void removeGuardianByID(UUID studentId, UUID requestId, boolean accept) {
        GuardianshipRequest request = requestRepository.findById(requestId).orElse(null);
        if (request != null) {
            if (accept) {
                requestRepository.setIsGuardianByID(studentId, request.getGuardian());
            } else {
                requestRepository.removeGuardianByStudentIdAndRequestId(studentId, requestId);
            }
        }
    }
}

