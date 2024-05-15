package attnftasap.adt.service;

import attnftasap.adt.model.Guardian;
import attnftasap.adt.model.Student;
import attnftasap.adt.repository.GuardianRepository;
import attnftasap.adt.repository.StudentRepository;
import attnftasap.adt.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.*;

@SpringBootTest
public class UserServiceImplTest {
    @Mock
    GuardianRepository guardianRepository;

    @Mock
    StudentRepository studentRepository;

    @Mock
    UserRepository userRepository;

    @InjectMocks
    UserServiceImpl userService;

    @Test
    void testRegisterStudentSuccess() {
        String username = "teststudent";
        String email = "test@example.com";
        String password = "password";
        Student student = new Student(username, email, password);
        Student savedStudent = new Student();
        savedStudent.setUserUUID(UUID.randomUUID());
        savedStudent.setUsername(username);
        savedStudent.setEmail(email);
        savedStudent.setPassword(password);

        when(userRepository.findFirstByUsername(username)).thenReturn(Optional.empty());
        when(studentRepository.save(student)).thenReturn(savedStudent);

        Student result = userService.registerStudent(student);

        verify(userRepository, times(1)).findFirstByUsername(username);
        verify(studentRepository, times(1)).save(student);
        assertEquals(savedStudent, result);
    }

    @Test
    void testRegisterStudentNullUsername() {
        String username = null;
        String email = "test@example.com";
        String password = "password";
        Student student = new Student(username, email, password);
        Student savedStudent = new Student();
        savedStudent.setUserUUID(UUID.randomUUID());
        savedStudent.setUsername(username);
        savedStudent.setEmail(email);
        savedStudent.setPassword(password);

        Student result = userService.registerStudent(student);

        verify(userRepository, times(0)).findFirstByUsername(username);
        verify(studentRepository, times(0)).save(student);
        assertNull(result);
    }

    @Test
    void testRegisterStudentNullEmail() {
        String username = "teststudent";
        String email = null;
        String password = "password";
        Student student = new Student(username, email, password);
        Student savedStudent = new Student();
        savedStudent.setUserUUID(UUID.randomUUID());
        savedStudent.setUsername(username);
        savedStudent.setEmail(email);
        savedStudent.setPassword(password);

        Student result = userService.registerStudent(student);

        verify(userRepository, times(0)).findFirstByUsername(username);
        verify(studentRepository, times(0)).save(student);
        assertNull(result);
    }

    @Test
    void testRegisterStudentNullPassword() {
        String username = "teststudent";
        String email = "test@example.com";
        String password = null;
        Student student = new Student(username, email, password);
        Student savedStudent = new Student();
        savedStudent.setUserUUID(UUID.randomUUID());
        savedStudent.setUsername(username);
        savedStudent.setEmail(email);
        savedStudent.setPassword(password);

        Student result = userService.registerStudent(student);

        verify(userRepository, times(0)).findFirstByUsername(username);
        verify(studentRepository, times(0)).save(student);
        assertNull(result);
    }

    @Test
    void testRegisterStudentDuplicate() {
        String username = "teststudent";
        String email = "test@example.com";
        String password = "password";
        Student student = new Student(username, email, password);
        Student savedStudent = new Student();
        savedStudent.setUserUUID(UUID.randomUUID());
        savedStudent.setUsername(username);
        savedStudent.setEmail(email);
        savedStudent.setPassword(password);

        when(userRepository.findFirstByUsername(username)).thenReturn(Optional.of(savedStudent));

        Student result = userService.registerStudent(student);

        verify(userRepository, times(1)).findFirstByUsername(username);
        verify(studentRepository, times(0)).save(student);
        assertNull(result);
    }

    @Test
    void testStudentAuthenticateFound() {
        String username = "testStudent";
        String password = "password";
        Student student = new Student();
        student.setUsername(username);
        student.setPassword(password);

        when(studentRepository.findByUsernameAndPassword(username, password)).thenReturn(Optional.of(student));

        Student result = userService.studentAuthenticate(username, password);

        verify(studentRepository, times(1)).findByUsernameAndPassword(username, password);
        assertEquals(student, result);
    }

    @Test
    void testStudentAuthenticateNotFound() {
        String username = "testStudent";
        String password = "password";
        Student student = new Student();
        student.setUsername(username);
        student.setPassword(password);

        when(studentRepository.findByUsernameAndPassword(username, password)).thenReturn(Optional.empty());

        Student result = userService.studentAuthenticate(username, password);

        verify(studentRepository, times(1)).findByUsernameAndPassword(username, password);
        assertNull(result);
    }

    @Test
    void testRegisterGuardianSuccess() {
        String username = "testGuardian";
        String email = "test@example.com";
        String password = "password";
        Guardian guardian = new Guardian(username, email, password);
        Guardian savedGuardian = new Guardian();
        savedGuardian.setUserUUID(UUID.randomUUID());
        savedGuardian.setUsername(username);
        savedGuardian.setEmail(email);
        savedGuardian.setPassword(password);

        when(userRepository.findFirstByUsername(username)).thenReturn(Optional.empty());
        when(guardianRepository.save(guardian)).thenReturn(savedGuardian);

        Guardian result = userService.registerGuardian(guardian);

        verify(userRepository, times(1)).findFirstByUsername(username);
        verify(guardianRepository, times(1)).save(guardian);
        assertEquals(savedGuardian, result);
    }

    @Test
    void testRegisterGuardianNullUsername() {
        String username = null;
        String email = "test@example.com";
        String password = "password";
        Guardian guardian = new Guardian(username, email, password);
        Guardian savedGuardian = new Guardian();
        savedGuardian.setUserUUID(UUID.randomUUID());
        savedGuardian.setUsername(username);
        savedGuardian.setEmail(email);
        savedGuardian.setPassword(password);

        Guardian result = userService.registerGuardian(guardian);

        verify(userRepository, times(0)).findFirstByUsername(username);
        verify(guardianRepository, times(0)).save(guardian);
        assertNull(result);
    }

    @Test
    void testRegisterGuardianNullEmail() {
        String username = "testGuardian";
        String email = null;
        String password = "password";
        Guardian guardian = new Guardian(username, email, password);
        Guardian savedGuardian = new Guardian();
        savedGuardian.setUserUUID(UUID.randomUUID());
        savedGuardian.setUsername(username);
        savedGuardian.setEmail(email);
        savedGuardian.setPassword(password);

        Guardian result = userService.registerGuardian(guardian);

        verify(userRepository, times(0)).findFirstByUsername(username);
        verify(guardianRepository, times(0)).save(guardian);
        assertNull(result);
    }

    @Test
    void testRegisterGuardianNullPassword() {
        String username = "testGuardian";
        String email = "test@example.com";
        String password = null;
        Guardian guardian = new Guardian(username, email, password);
        Guardian savedGuardian = new Guardian();
        savedGuardian.setUserUUID(UUID.randomUUID());
        savedGuardian.setUsername(username);
        savedGuardian.setEmail(email);
        savedGuardian.setPassword(password);

        Guardian result = userService.registerGuardian(guardian);

        verify(userRepository, times(0)).findFirstByUsername(username);
        verify(guardianRepository, times(0)).save(guardian);
        assertNull(result);
    }

    @Test
    void testRegisterGuardianDuplicate() {
        String username = "testGuardian";
        String email = "test@example.com";
        String password = "password";
        Guardian guardian = new Guardian(username, email, password);
        Guardian savedGuardian = new Guardian();
        savedGuardian.setUserUUID(UUID.randomUUID());
        savedGuardian.setUsername(username);
        savedGuardian.setEmail(email);
        savedGuardian.setPassword(password);

        when(userRepository.findFirstByUsername(username)).thenReturn(Optional.of(savedGuardian));

        Guardian result = userService.registerGuardian(guardian);

        verify(userRepository, times(1)).findFirstByUsername(username);
        verify(guardianRepository, times(0)).save(guardian);
        assertNull(result);
    }

    @Test
    void testGuardianAuthenticateFound() {
        String username = "testGuardian";
        String password = "password";
        Guardian guardian = new Guardian();
        guardian.setUsername(username);
        guardian.setPassword(password);

        when(guardianRepository.findByUsernameAndPassword(username, password)).thenReturn(Optional.of(guardian));

        Guardian result = userService.guardianAuthenticate(username, password);

        verify(guardianRepository, times(1)).findByUsernameAndPassword(username, password);
        assertEquals(guardian, result);
    }

    @Test
    void testGuardianAuthenticateNotFound() {
        String username = "testGuardian";
        String password = "password";
        Guardian guardian = new Guardian();
        guardian.setUsername(username);
        guardian.setPassword(password);

        when(guardianRepository.findByUsernameAndPassword(username, password)).thenReturn(Optional.empty());

        Guardian result = userService.guardianAuthenticate(username, password);

        verify(guardianRepository, times(1)).findByUsernameAndPassword(username, password);
        assertNull(result);
    }
}
