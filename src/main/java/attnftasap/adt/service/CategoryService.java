package attnftasap.adt.service;

import attnftasap.adt.model.Category;
import attnftasap.adt.model.Student;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.Month;
import java.util.List;
import java.util.UUID;

@Service
public interface CategoryService {
    boolean createCategory(String categoryName,  Student student);
    void deleteCustomCategory(UUID categoryID);
    Category findCategoryFromStudent(String name, Student student);
    List<Category> findAllCategoriesForStudent(Student student);

    Category findCategoryByStudentAndName(Student student, String name);
}
