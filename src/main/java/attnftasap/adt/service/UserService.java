package attnftasap.adt.service;

import attnftasap.adt.model.Guardian;
import attnftasap.adt.model.Student;

public interface UserService {
    Student studentAuthenticate(String username, String password);
    Guardian guardianAuthenticate(String username, String password);
    Student registerStudent(Student student);
    Guardian registerGuardian(Guardian guardian);
}
