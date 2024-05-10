package attnftasap.adt.service;

import attnftasap.adt.model.Guardian;
import attnftasap.adt.model.GuardianshipRequest;
import attnftasap.adt.model.Student;
import attnftasap.adt.repository.RequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class RequestServiceImpl implements RequestService {

    @Autowired
    private RequestRepository requestRepository;

    @Override
    public List<GuardianshipRequest> getGuardianRequestsByID(UUID studentId) {
        return requestRepository.findAllById(studentId);
    }

    @Override
    public Guardian getIsGuardianByID(UUID studentId) {
        return requestRepository.findGuardianByStudentId(studentId);
    }

    @Override
    @Transactional
    public void removeGuardianByID(UUID studentId, boolean accept) {
        if (accept) {
            Guardian guardian = requestRepository.findGuardianByStudentId(studentId);
            if (guardian != null) {
                requestRepository.removeGuardianByStudentID(studentId);
                requestRepository.setIsGuardianByID(studentId, guardian);
            }
        } else {
            requestRepository.removeGuardianByStudentID(studentId);
        }
    }
}
