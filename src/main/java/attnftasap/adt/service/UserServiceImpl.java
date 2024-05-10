package attnftasap.adt.service;

import attnftasap.adt.model.Guardian;
import attnftasap.adt.model.Student;
import attnftasap.adt.model.User;
import attnftasap.adt.repository.GuardianRepository;
import attnftasap.adt.repository.StudentRepository;
import attnftasap.adt.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService{
    @Autowired
    StudentRepository studentRepository;

    @Autowired
    GuardianRepository guardianRepository;

    @Autowired
    UserRepository userRepository;

    @Override
    public Student registerStudent(Student student) {
        if (checkValidUser(student)) {
            if (userRepository.findFirstByUsername(student.getUsername()).isPresent()) {
                System.out.println("Duplicate user, please enter a different username");
                return null;
            }
            return studentRepository.save(student);
        } else {
            return null;
        }
    }

    @Override
    public Guardian registerGuardian(Guardian guardian) {
        if (checkValidUser(guardian)) {
            if (userRepository.findFirstByUsername(guardian.getUsername()).isPresent()) {
                System.out.println("Duplicate user, please enter a different username");
                return null;
            }
            return guardianRepository.save(guardian);
        } else {
            return null;
        }
    }

    private boolean checkValidUser(User user) {
        return user.getUsername() != null && user.getPassword() != null && user.getEmail() != null;
    }

    @Override
    public Student studentAuthenticate(String username, String password) {
        return studentRepository.findByUsernameAndPassword(username, password).orElse(null);
    }

    @Override
    public Guardian guardianAuthenticate(String username, String password) {
        return guardianRepository.findByUsernameAndPassword(username, password).orElse(null);
    }
}
