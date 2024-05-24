package attnftasap.adt.service;

import attnftasap.adt.model.Student;

public interface StudentService {
    Student findByUsername(String username);
}
