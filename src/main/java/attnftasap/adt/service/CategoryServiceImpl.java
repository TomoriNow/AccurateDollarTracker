package attnftasap.adt.service;

import attnftasap.adt.model.Budget;
import attnftasap.adt.model.Category;
import attnftasap.adt.model.Student;
import attnftasap.adt.repository.CategoryRepository;
import attnftasap.adt.repository.StudentRepository;
import attnftasap.adt.repository.BudgetRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Month;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private BudgetRepository budgetRepository;

    @Autowired
    private BudgetService budgetService;

    @Transactional
    public boolean createCategory(String categoryName, Student student) {
        // Create a new Category
        if (categoryRepository.findByStudentAndName(student, categoryName) == null){
            Category category = new Category(student, categoryName);
            categoryRepository.save(category);
            return true;
        }
        return false;
    }

    @Override
    @Transactional
    public void deleteCustomCategory(UUID categoryId) {
        budgetRepository.deleteByCategoryUUID(categoryId);
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

    @Override
    public Category findCategoryByStudentAndName(Student student, String name) {
        return categoryRepository.findByStudentAndName(student, name);
    }
}
