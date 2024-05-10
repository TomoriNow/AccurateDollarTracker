package attnftasap.adt.service;

import attnftasap.adt.model.Guardian;
import attnftasap.adt.model.GuardianshipRequest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class RequestServiceImplTest {

    @Mock
    private RequestRepository requestRepository;

    @InjectMocks
    private RequestServiceImpl requestService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void getGuardianRequestsByID() {
        UUID studentId = UUID.randomUUID();
        List<GuardianshipRequest> expectedRequests = new ArrayList<>();
        when(requestRepository.findAllById(studentId)).thenReturn(expectedRequests);

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
    void removeGuardianByID_accept() {
        UUID studentId = UUID.randomUUID();
        Guardian guardian = new Guardian();
        when(requestRepository.findGuardianByStudentId(studentId)).thenReturn(guardian);

        requestService.removeGuardianByID(studentId, true);

        verify(requestRepository, times(1)).removeGuardianByStudentID(studentId);
        verify(requestRepository, times(1)).setIsGuardianByID(studentId, guardian);
    }

    @Test
    void removeGuardianByID_reject() {
        UUID studentId = UUID.randomUUID();

        requestService.removeGuardianByID(studentId, false);

        verify(requestRepository, times(1)).removeGuardianByStudentID(studentId);
        verify(requestRepository, never()).setIsGuardianByID(any(), any());
    }
}
