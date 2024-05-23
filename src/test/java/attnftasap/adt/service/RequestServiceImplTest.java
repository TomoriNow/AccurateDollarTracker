package attnftasap.adt.service;

import attnftasap.adt.model.Guardian;
import attnftasap.adt.model.GuardianshipRequest;
import attnftasap.adt.repository.RequestRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import jakarta.transaction.Transactional;
import java.util.Optional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class RequestServiceImplTest {

    @Mock
    private RequestRepository requestRepository;

    @InjectMocks
    private RequestServiceImpl requestService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getGuardianRequestsByID() {
        UUID studentId = UUID.randomUUID();
        List<GuardianshipRequest> expectedRequests = new ArrayList<>();
        when(requestRepository.findAllByStudentID(studentId)).thenReturn(expectedRequests);

        List<GuardianshipRequest> actualRequests = requestService.getGuardianRequestsByID(studentId);

        assertEquals(expectedRequests, actualRequests);
    }

    @Test
    void getIsGuardianByID() {
        UUID studentId = UUID.randomUUID();
        Guardian expectedGuardian = new Guardian();
        when(requestRepository.findGuardianByStudentId(studentId)).thenReturn(expectedGuardian);

        Guardian actualGuardian = requestService.getIsGuardianByID(studentId);

        assertEquals(expectedGuardian, actualGuardian);
    }

    @Test
    @Transactional
    public void testRemoveGuardianByID_AcceptTrue() {
        UUID studentId = UUID.randomUUID();
        UUID requestId = UUID.randomUUID();
        Guardian guardian = new Guardian();
        GuardianshipRequest request = new GuardianshipRequest();
        request.setGuardian(guardian);

        when(requestRepository.findById(requestId)).thenReturn(Optional.of(request));

        requestService.removeGuardianByID(studentId, requestId, true);

        verify(requestRepository, times(1)).setIsGuardianByID(studentId, guardian);
        verify(requestRepository, never()).removeGuardianByStudentIdAndRequestId(studentId, requestId);
    }

    @Test
    @Transactional
    public void testRemoveGuardianByID_AcceptFalse() {
        UUID studentId = UUID.randomUUID();
        UUID requestId = UUID.randomUUID();

        GuardianshipRequest request = new GuardianshipRequest();
        when(requestRepository.findById(requestId)).thenReturn(Optional.of(request));

        requestService.removeGuardianByID(studentId, requestId, false);

        verify(requestRepository, never()).setIsGuardianByID(any(), any());
        verify(requestRepository, times(1)).removeGuardianByStudentIdAndRequestId(studentId, requestId);
    }
}