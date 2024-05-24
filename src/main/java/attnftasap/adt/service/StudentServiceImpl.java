package attnftasap.adt.service;

import attnftasap.adt.model.Student;
import attnftasap.adt.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StudentServiceImpl implements StudentService {

    @Autowired
    private StudentRepository studentRepository;

    @Override
    public Student findByUsername(String username) {
        return studentRepository.findByUsername(username);
    }
}
