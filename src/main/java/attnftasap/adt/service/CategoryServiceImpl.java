package attnftasap.adt.service;

import attnftasap.adt.model.Category;
import attnftasap.adt.model.Student;
import attnftasap.adt.repository.CategoryRepository;
import jakarta.persistence.Inheritance;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Transactional
    public void createCategory(String categoryName) {
        Category category = new Category();
        category.setName(categoryName);

        categoryRepository.save(category);
    }

    @Override
    @Transactional
    public void deleteCustomCategory(UUID categoryId) {
        categoryRepository.deleteCustomCategoryByID(categoryId);
    }

    @Override
    public Category findCategoryFromStudent(String name, Student student) {
        List<Category> categoryList = student.getCategories();
        for (Category category: categoryList) {
            if (category.getName().equals(name)) {
                return category;
            }
        }
        return null;
    }

    @Override
    public List<Category> findAllCategoriesForStudent(Student student) {
        return categoryRepository.findByStudent(student);
    }
}
