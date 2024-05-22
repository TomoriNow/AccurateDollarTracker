package attnftasap.adt.service;

import attnftasap.adt.model.Category;
import attnftasap.adt.model.Student;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public interface CategoryService {
    void createCategory(String categoryName);
    void deleteCustomCategory(UUID categoryID);
    Category findCategoryFromStudent(String name, Student student);
    List<Category> findAllCategoriesForStudent(Student student);
}
